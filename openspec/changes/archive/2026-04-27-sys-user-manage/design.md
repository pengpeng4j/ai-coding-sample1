# 技术设计

## 1. 数据库设计

### 1.1 表结构

```sql
CREATE TABLE `sys_user` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name`   VARCHAR(64)  NOT NULL COMMENT '用户名',
  `password`    VARCHAR(128) NOT NULL COMMENT '密码',
  `user_phone`  VARCHAR(20)  NOT NULL COMMENT '手机号',
  `user_email`  VARCHAR(128)          COMMENT '邮箱',
  `user_type`   TINYINT      NOT NULL DEFAULT 0 COMMENT '用户类型(0:普通用户, 1:系统用户)',
  `user_status` TINYINT      NOT NULL DEFAULT 1 COMMENT '用户状态(0:禁用, 1:启用, 2:删除)',
  `user_avatar` VARCHAR(256)          COMMENT '头像',
  `create_by`   VARCHAR(64)  NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by`   VARCHAR(64)  NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_name`),
  UNIQUE KEY `uk_user_phone` (`user_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

### 1.2 索引策略

| 索引名 | 类型 | 字段 | 说明 |
|--------|------|------|------|
| PRIMARY | PK | id | 主键 |
| uk_user_name | UNIQUE | user_name | 用户名唯一 |
| uk_user_phone | UNIQUE | user_phone | 手机号唯一 |

逻辑删除通过 `user_status = 2` 实现，查询时加 `user_status != 2` 条件过滤。

## 2. 分层架构

```
┌───────────────────────────────────────────────────────┐
│ Controller Layer                                       │
│  SysUserManageController                               │
│    - createUser       → SysUserManageService           │
│    - modifyUserById   → SysUserManageService           │
│    - changeStatusById → SysUserManageService           │
│    - removeUserById   → SysUserManageService           │
│    - queryUserById    → SysUserManageService           │
│    - queryUserByPage  → SysUserManageService           │
├───────────────────────────────────────────────────────┤
│ Biz Layer (管理场景编排)                                 │
│  SysUserManageServiceImpl                              │
│    - 参数校验增强                                        │
│    - 操作人信息填充                                       │
│    - 调用 SysUserService 领域方法                         │
│    - DTO 转换 (MapStruct)                                │
├───────────────────────────────────────────────────────┤
│ Domain Layer (领域操作)                                  │
│  SysUserService                                        │
│    - insertUser          → SysUserMapper               │
│    - updateUserById      → SysUserMapper               │
│    - changeUserStatusById → SysUserMapper              │
│    - deleteUserById      → SysUserMapper               │
│    - selectUserById      → SysUserMapper               │
│    - selectUserByPage    → SysUserMapper               │
│    - selectUserByName    → SysUserMapper               │
├───────────────────────────────────────────────────────┤
│ DAL Layer                                              │
│  SysUserMapper extends BaseMapper<SysUserDO>            │
│    - 基础 CRUD 继承 BaseMapper                           │
│    - 分页查询使用 MyBatis-Plus QueryWrapper               │
├───────────────────────────────────────────────────────┤
│ Mapping Layer                                          │
│  SysUserMapping (MapStruct)                             │
│    - toDTO(SysUserDO)       → SysUserDTO               │
│    - toDO(SysUserCreateRequest) → SysUserDO            │
│    - toQuery(SysUserPageRequest)  → SysUserQuery       │
└───────────────────────────────────────────────────────┘
```

## 3. 各层职责划分

### 3.1 Controller
- 接收 Request，做 `@Validated` 基础校验
- 调用 ManageService，返回 `DefaultResponse` / `PageResponse`
- 不包含业务逻辑

### 3.2 ManageService (biz)
- 场景编排层，处理管理端特定逻辑
- 参数校验（如用户名重复、状态合法性）
- 调用 domain service 完成领域操作
- 操作人信息注入（`createBy` / `updateBy`）
- DO ↔ DTO 转换

### 3.3 UserService (domain)
- 纯粹的数据操作，直接对接 Mapper
- 方法命名遵循：`insertXXX` / `updateXXXById` / `deleteXXXById` / `selectXXXById` / `selectXXXByPage`
- 不处理场景特定逻辑

### 3.4 Mapper (dal)
- 继承 `BaseMapper<SysUserDO>`
- 复杂查询使用 `QueryWrapper` / `LambdaQueryWrapper`

## 4. 关键设计决策

### 4.1 逻辑删除
不使用物理删除，`user_status = 2` 表示已删除。所有查询自动过滤 `user_status != 2`。

### 4.2 密码存储
密码在创建时不加密（后续可接入加密策略），当前阶段明文存储以简化实现。

### 4.3 操作人追踪
`AbstractManageService` 提供 `getOperator()` 方法返回默认操作人 `SYSTEM`。ManageServiceImpl 使用该方法填充 `createBy` / `updateBy` 字段。

### 4.4 响应 Builder 模式

`DefaultResponse` 和 `PageResponse` 统一使用 Builder 模式，入口方法为 `ok()` / `error()`，终态调用 `.build()`。

```java
// 成功
DefaultResponse.<T>ok(data).build();

// 失败
DefaultResponse.<T>error("400", "错误消息").build();

// 分页
PageResponse.<T>ok(dataList, total).build();
PageResponse.<T>error("500", "查询失败").build();
```

**实现要点：**
- `ok(T data)` / `ok(List<T> data, long total)` 返回 `Builder` 实例，预设 `success = true`
- `error(String code, String message)` 返回 `Builder` 实例，预设 `success = false`
- Builder 提供 `code()` / `message()` / `data()` 等链式方法供扩展
- `build()` 返回不可变的 Response 实例

## 5. Request → Service 映射

| 接口 | Request | ManageService方法 | 校验逻辑 |
|------|---------|-------------------|---------|
| createUser | SysUserCreateRequest | create | 用户名不重复、手机号不重复 |
| modifyUserById | SysUserModifyRequest | modify | ID存在、状态非删除 |
| changeStatusById | SysUserStatusRequest | changeStatus | ID存在、新状态合法 |
| removeUserById | SysUserRemoveRequest | remove | ID存在、状态非删除 |
| queryUserById | SysUserDetailRequest | getById | ID存在 |
| queryUserByPage | SysUserPageRequest | queryByPage | 无特殊校验 |
