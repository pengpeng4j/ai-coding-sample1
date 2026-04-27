# 管理端 API 规范

## Requirements

### Requirement: API 统一规范

系统 **必须** 遵循统一的 API 设计规范。

#### Scenario: 接口路径

- **那么** 所有管理端接口路径以 `/manage/` 开头
- **并且** 资源路径使用小写复数形式（如 `/manage/sysuser`）

#### Scenario: 请求方法

- **那么** 所有管理端接口统一使用 `POST` 方法

#### Scenario: 响应格式

- **那么** 成功响应使用 `DefaultResponse.success(code, message, data)`
- **并且** 失败响应使用 `DefaultResponse.error(code, message)`
- **并且** 分页响应使用 `PageResponse`，包含 `dataList` 和 `total`

#### Scenario: 参数校验

- **那么** 所有接口使用 `@Validated` 注解触发 Bean Validation
- **并且** 校验失败由全局异常处理器统一处理
