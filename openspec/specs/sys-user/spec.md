# SysUser 领域模型规范

## Requirements

### Requirement: 创建系统用户

系统 **必须** 支持通过管理端接口创建新用户。

#### Scenario: 创建成功

- **当** 请求包含合法的 `userName`、`password`、`userPhone`
- **且** `userName` 和 `userPhone` 未被其他用户占用
- **那么** 创建成功，返回用户详情数据
- **并且** `userType` 默认为 0（普通用户）
- **并且** `userStatus` 默认为 1（启用）
- **并且** `createBy` 为当前操作人

#### Scenario: 用户名已存在

- **当** `userName` 已被其他用户使用
- **那么** 返回错误响应，提示"用户名已存在"

#### Scenario: 手机号已存在

- **当** `userPhone` 已被其他用户使用
- **那么** 返回错误响应，提示"手机号已存在"

---

### Requirement: 修改用户信息

系统 **必须** 支持通过 ID 修改用户信息。

#### Scenario: 修改成功

- **当** 用户 ID 存在且状态不为"已删除"
- **且** 修改的用户名/手机号（如有）未被其他用户占用
- **那么** 更新成功，返回更新后的用户数据

#### Scenario: 用户不存在

- **当** 用户 ID 不存在
- **那么** 返回错误响应，提示"用户不存在"

#### Scenario: 用户已删除

- **当** 用户状态为"已删除"(user_status=2)
- **那么** 返回错误响应，提示"用户已删除，不可修改"

---

### Requirement: 变更用户状态

系统 **必须** 支持通过 ID 变更用户状态（启用/禁用/删除）。

#### Scenario: 状态变更成功

- **当** 用户 ID 存在
- **且** 新状态为 0（禁用）、1（启用）或 2（删除）之一
- **那么** 更新成功，返回 `true`

#### Scenario: 状态值非法

- **当** 新状态不在 [0, 1, 2] 范围内
- **那么** 返回错误响应

---

### Requirement: 删除用户

系统 **必须** 支持通过 ID 逻辑删除用户。

#### Scenario: 删除成功

- **当** 用户 ID 存在
- **且** 用户状态不为"已删除"
- **那么** 将 `userStatus` 设为 2（删除），返回 `true`

#### Scenario: 用户已删除

- **当** 用户状态已经是 2（删除）
- **那么** 返回错误响应，提示"用户已删除"

---

### Requirement: 查询用户详情

系统 **必须** 支持通过 ID 查询用户详情。

#### Scenario: 查询成功

- **当** 用户 ID 存在
- **且** 用户状态不为"已删除"
- **那么** 返回用户详情（SysUserDTO）

---

### Requirement: 分页查询用户列表

系统 **必须** 支持分页查询用户列表。

#### Scenario: 查询成功

- **当** 提供 `pageNum` 和 `pageSize`
- **那么** 返回分页结果，包含 `dataList` 和 `total`
- **并且** 自动过滤 `user_status = 2`（已删除）的用户
- **并且** 支持按 `userName`、`userType`、`userStatus` 条件筛选

---

### Requirement: 数据模型

系统 **必须** 定义以下数据模型：

| 模型 | 职责 | 关键字段 |
|------|------|---------|
| SysUserDO | 数据库实体映射 | id, userName, password, userPhone, userEmail, userType, userStatus, userAvatar, createBy, createTime, updateBy, updateTime |
| SysUserDTO | API 响应模型 | 与 SysUserDO 字段一致，但隐藏 password |
| SysUserQuery | 查询条件对象 | 继承 SysUserDO，额外支持 userStatuses 集合 |

### Request 模型字段定义

| Request | 必填字段 | 可选字段 |
|---------|---------|---------|
| SysUserCreateRequest | userName, password, userPhone | userEmail, userType, userAvatar |
| SysUserModifyRequest | id | userName, userPhone, userEmail, userAvatar |
| SysUserStatusRequest | id, userStatus | — |
| SysUserRemoveRequest | id | — |
| SysUserDetailRequest | id | — |
| SysUserPageRequest | (继承 pageNum=1, pageSize=10) | userName, userType, userStatus |
