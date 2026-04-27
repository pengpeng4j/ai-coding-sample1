# 实施任务清单

## Phase 1: 数据层 (DAL + Domain)

- [x] 1.1 创建 `SysUserMapper.java`，继承 `BaseMapper<SysUserDO>`
- [x] 1.2 更新 `SysUserDO.java`，删除 `env` 字段，添加 `@TableName("sys_user")` 和 `@TableField` 注解
- [x] 1.3 实现 `SysUserService` 领域服务，完成 `insertUser` / `updateUserById` / `changeUserStatusById` / `deleteUserById` / `selectUserById` / `selectUserByPage` / `selectUserByName` / `selectUserByPhone` 方法
- [x] 1.4 核对数据库表结构与 DO 字段是否一致（表已建好，无需创建）

## Phase 2: 映射层 (Mapping)

- [x] 2.1 实现 `SysUserMapping`，完成 `toDTO` / `toCreateDO` / `toModifyDO` / `toQuery` 方法
- [x] 2.2 补齐 `SysUserDTO` 字段定义（隐藏 password 字段）

## Phase 3: 请求层 (Request)

- [x] 3.1 补齐 `SysUserCreateRequest` 字段
- [x] 3.2 补齐 `SysUserModifyRequest` 字段
- [x] 3.3 补齐 `SysUserStatusRequest` 字段
- [x] 3.4 补齐 `SysUserPageRequest` 查询条件字段

## Phase 4: 业务层 (Biz)

- [x] 4.1 实现 `SysUserManageService` 接口，定义 6 个管理方法
- [x] 4.2 实现 `SysUserManageServiceImpl`，完成所有方法的编排逻辑（校验 → 调用 domain service → 转换 DTO）

## Phase 5: 控制层 (Controller)

- [x] 5.1 实现 `AbstractController` 中的 TODO 方法（`defaultResponse` / `pageResponse` / `getCurrentRequest` 等）
- [x] 5.2 实现 `SysUserManageController` 中的 6 个 TODO 方法，调用 ManageService

## Phase 6: 规范沉淀

- [x] 6.1 创建 `.claude/rules/` 目录
- [x] 6.2 编写 `manage-crud.md` 领域模型后台管理开发规范（分层约定、命名规范、Request/DTO/Mapper 生成规则、Service 编排模式）
- [x] 6.3 更新 `CLAUDE.md` 引入 rules 目录引用

## 附加

- [x] Lombok 版本从 1.18.26 升级到 1.18.38（兼容 JDK 25）
