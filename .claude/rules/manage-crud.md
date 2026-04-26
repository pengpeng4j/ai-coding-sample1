# 领域模型后台管理开发规范

本文档定义新增领域模型后台管理功能的标准开发流程。所有新领域模型的后台管理功能必须遵循此规范。

---

## 1. 分层架构

```
Controller → ManageService(biz) → UserService(domain) → Mapper(dal)
```

| 层级 | 包路径 | 职责 | 禁止 |
|------|--------|------|------|
| **Controller** | `sample.api.controller` | 接收请求、参数校验、调用 ManageService、返回 Response | 不包含业务逻辑 |
| **ManageService** | `sample.biz.service` | 场景编排、业务校验、操作人注入、DTO 转换 | 不直接操作 Mapper |
| **UserService** | `sample.domain.service` | 纯粹的数据操作，直接对接 Mapper | 不处理场景逻辑 |
| **Mapper** | `sample.dal.mapper` | MyBatis-Plus BaseMapper 继承 | 不写原生 SQL（除非必要） |
| **Mapping** | `sample.mapping` | MapStruct 抽象类，DO/DTO/Request 互转 | 不使用实现类手写转换 |

---

## 2. 命名规范

### 2.1 类命名

| 类型 | 命名模板 | 示例 |
|------|----------|------|
| 领域对象 | `{Domain}DO` | `SysUserDO` |
| DTO | `{Domain}DTO` | `SysUserDTO` |
| 查询对象 | `{Domain}Query` | `SysUserQuery` |
| Controller | `{Domain}ManageController` | `SysUserManageController` |
| 管理服务接口 | `{Domain}ManageService` | `SysUserManageService` |
| 管理服务实现 | `{Domain}ManageServiceImpl` | `SysUserManageServiceImpl` |
| 领域服务 | `{Domain}Service` | `SysUserService` |
| Mapper | `{Domain}Mapper` | `SysUserMapper` |
| MapStruct | `{Domain}Mapping` | `SysUserMapping` |

### 2.2 Request 命名

| 操作 | 命名 | 示例 |
|------|------|------|
| 创建 | `{Domain}CreateRequest` | `SysUserCreateRequest` |
| 修改 | `{Domain}ModifyRequest` | `SysUserModifyRequest` |
| 状态变更 | `{Domain}StatusRequest` | `SysUserStatusRequest` |
| 删除 | `{Domain}RemoveRequest` | `SysUserRemoveRequest` |
| 详情查询 | `{Domain}DetailRequest` | `SysUserDetailRequest` |
| 分页查询 | `{Domain}PageRequest` | `SysUserPageRequest` |

### 2.3 领域服务方法命名

| 操作 | 命名模板 | 示例 |
|------|----------|------|
| 新增 | `insert{Domain}` | `insertUser` |
| 修改 | `update{Domain}ById` | `updateUserById` |
| 状态变更 | `change{Domain}StatusById` | `changeUserStatusById` |
| 逻辑删除 | `delete{Domain}ById` | `deleteUserById` |
| ID 查询 | `select{Domain}ById` | `selectUserById` |
| 条件分页 | `select{Domain}ByPage` | `selectUserByPage` |
| 唯一字段查询 | `select{Domain}By{Field}` | `selectUserByName`、`selectUserByPhone` |

**注意**：方法名中的 `{Domain}` 使用领域名称关键字（如 `User`），而非完整前缀（不用 `SysUser`）。

### 2.4 管理服务方法命名

**核心规则：XxxManageService 的方法名必须与 Controller 中的方法名严格一致。**

| 操作 | 命名模板 | 示例 |
|------|----------|------|
| 创建 | `{Controller方法名}` | `createUser` |
| 修改 | `{Controller方法名}` | `modifyUserById` |
| 状态变更 | `{Controller方法名}` | `changeStatusById` |
| 删除 | `{Controller方法名}` | `removeUserById` |
| 详情查询 | `{Controller方法名}` | `queryUserById` |
| 分页查询 | `{Controller方法名}` | `queryUserByPage` |

Controller 调用示例：
```java
// Controller
@PostMapping("/createUser")
public DefaultResponse<SysUserDTO> createUser(...) {
    return defaultResponse(sysUserManageService.createUser(request));  // 同名
}
```

---

## 3. 数据模型规范

### 3.1 DO（领域对象）

```java
@Getter @Setter @ToString
@TableName("表名")
public class XxxDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("字段名")
    private String fieldName;

    // 公共字段：createBy, createTime, updateBy, updateTime
}
```

- 必须标注 `@TableName` 和 `@TableField`
- 不暴露 `password` 等敏感字段到 DTO
- 逻辑删除使用状态字段（如 `userStatus = 2`），不用物理删除

### 3.2 DTO（API 响应）

- 字段与 DO 对齐，但**隐藏敏感字段**（如 password）
- 不继承 DO，独立定义

### 3.3 Request

- 必须继承 `BaseRequest`（或 `PageRequest` 用于分页）
- 必填字段使用 `@NotBlank` / `@NotNull` + `message` 属性
- 创建 Request：包含所有创建时必填/可选字段
- 修改 Request：包含 `id` + 可修改字段
- 分页 Request：继承 `PageRequest`，添加查询条件字段

### 3.4 Query

- 继承 DO，扩展查询条件字段
- **不包含** `pageNum` 和 `pageSize` 字段
- 分页参数通过 MyBatis-Plus 的 `Page<SysUserDO>` 对象传递

---

## 4. Mapper 规范

```java
public interface SysUserMapper extends BaseMapper<SysUserDO> {
}
```

- 继承 `BaseMapper<DO>` 即可获得所有基础 CRUD
- 复杂查询使用 `LambdaQueryWrapper`
- 包扫描已由 `MybatisConfiguration` 配置，无需额外注解

### 4.1 QueryWrapper 条件查询判空规范

必须使用以下工具类进行判空，禁止使用 `!= null` 内联判断：

| 类型 | 判空方法 | 示例 |
|------|----------|------|
| 字符串 | `StringUtils.isNotBlank()` | `wrapper.eq(StringUtils.isNotBlank(name), Field::getName, name)` |
| 集合 | `CollectionUtils.isNotEmpty()` | `wrapper.in(CollectionUtils.isNotEmpty(ids), Field::getId, ids)` |
| 对象/基本类型包装类 | `Objects.nonNull()` | `wrapper.eq(Objects.nonNull(status), Field::getStatus, status)` |

示例：
```java
if (Objects.nonNull(query)) {
    wrapper.eq(StringUtils.isNotBlank(query.getUserName()), SysUserDO::getUserName, query.getUserName());
    wrapper.eq(Objects.nonNull(query.getUserType()), SysUserDO::getUserType, query.getUserType());
    wrapper.in(CollectionUtils.isNotEmpty(query.getUserStatuses()), SysUserDO::getUserStatus, query.getUserStatuses());
}
```

---

## 5. Mapping 规范

```java
@Mapper(componentModel = "spring", uses = {DateRelatedMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SysUserMapping {

    public abstract SysUserDTO toDTO(SysUserDO source);
    public abstract SysUserDO toCreateDO(SysUserCreateRequest source);
    public abstract SysUserDO toModifyDO(SysUserModifyRequest source);
    public abstract SysUserQuery toQuery(SysUserPageRequest source);

}
```

- 必须为抽象类，使用 Spring Bean 注入
- `nullValuePropertyMappingStrategy = IGNORE` 防止 null 覆盖已有值

---

## 5.1 依赖注入规范

- **禁止使用构造方法注入**，避免注入组件过多导致构造方法参数膨胀
- **统一使用 `@Resource` 注解**进行字段注入
- 注入字段声明为 `private`，不需要 `final`

```java
@Service
public class SysUserManageServiceImpl extends AbstractManageService implements SysUserManageService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapping sysUserMapping;

}
```

---

## 6. Service 编排规范

### 6.1 ManageServiceImpl 标准流程

```
校验（唯一性、存在性、状态合法性）
  → 参数转换（Request → DO）
  → 填充公共字段（createBy/updateBy/operator）
  → 调用 domain service
  → 结果转换（DO → DTO）
  → 返回
```

### 6.2 异常处理

- 业务异常统一抛出 `IllegalArgumentException`，message 为中文提示
- 后续由全局异常处理器统一拦截转为 error response

### 6.3 操作人注入

- 继承 `AbstractManageService`，使用 `getOperator()` 获取操作人
- 创建时同时设置 `createBy` 和 `updateBy`

### 6.4 分页查询规范

- 领域服务方法签名：`select{Domain}ByPage(SysUserQuery query, Page<SysUserDO> page)`
- 分页参数通过 MyBatis-Plus `Page` 对象传入，**不**放在 `XxxQuery` 中
- ManageService 中构建 `Page` 对象传入：`new Page<>(request.getPageNum(), request.getPageSize())`
- 必须在 `MybatisConfiguration` 中手动注册 `MybatisPlusInterceptor` + `PaginationInnerInterceptor`，分页插件**不会自动生效**

```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
}
```

---

## 7. Controller 规范

- 继承 `AbstractController`
- 所有接口使用 `POST` 方法
- 参数使用 `@RequestBody @Validated` 校验
- 通过 `defaultResponse(data)` 包装成功响应
- 通过 `defaultResponse("code", "message")` 包装失败响应

---

## 8. 响应规范

`DefaultResponse` 和 `PageResponse` 使用 Builder 模式：

```java
// 成功
DefaultResponse.<T>ok(data).build();

// 失败
DefaultResponse.<T>error("400", "错误消息").build();

// 分页成功
PageResponse.<T>ok(dataList, total).build();

// 分页失败
PageResponse.<T>error("500", "查询失败").build();
```

---

## 9. 新增领域模型清单

当需要新增一个领域模型（如 `SysRole`）的后台管理时，需要创建/修改以下文件：

| # | 文件 | 操作 |
|---|------|------|
| 1 | `domain/model/SysRoleDO.java` | 新建，加 `@TableName` |
| 2 | `domain/model/SysRoleQuery.java` | 新建，继承 DO |
| 3 | `domain/service/SysRoleService.java` | 新建，实现领域方法 |
| 4 | `dal/mapper/SysRoleMapper.java` | 新建，继承 `BaseMapper` |
| 5 | `mapping/SysRoleMapping.java` | 新建，MapStruct 抽象类 |
| 6 | `api/model/SysRoleDTO.java` | 新建 |
| 7 | `api/request/SysRoleCreateRequest.java` | 新建 |
| 8 | `api/request/SysRoleModifyRequest.java` | 新建 |
| 9 | `api/request/SysRoleStatusRequest.java` | 新建 |
| 10 | `api/request/SysRoleRemoveRequest.java` | 新建 |
| 11 | `api/request/SysRoleDetailRequest.java` | 新建 |
| 12 | `api/request/SysRolePageRequest.java` | 新建 |
| 13 | `biz/service/SysRoleManageService.java` | 新建接口 |
| 14 | `biz/service/impl/SysRoleManageServiceImpl.java` | 新建实现 |
| 15 | `api/controller/SysRoleManageController.java` | 新建 |
