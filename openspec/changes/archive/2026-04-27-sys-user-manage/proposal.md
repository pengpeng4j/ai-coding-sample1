# SysUser 后台管理功能

## 背景

项目已搭建好 Spring Boot 3.5 的分层骨架（Controller → ManageService(biz) → UserService(domain) → Mapper(dal)），但所有业务实现均为空。需要以 `SysUser`（系统用户）为第一个领域模型，完成完整的后台管理功能实现。

## 目标

1. 完成 `SysUser` 的 6 个管理端接口（创建、修改、状态变更、删除、详情查询、分页查询）
2. 补齐各层缺失的代码（DTO、Request、Mapper、Service 实现、建表 SQL）
3. 沉淀"领域模型后台管理开发规范"到 `.claude/rules/`，作为后续其他领域模型后台功能的参照基石

## 非目标

- 不完成 Open API 端点（`SysUserOpenapiController`）的实现
- 不涉及用户认证、权限控制（鉴权后续单独做）
- 不涉及前端页面开发

## 技术选型

- ORM: MyBatis-Plus 3.5.14（方案A：注解 + BaseMapper）
- DTO 映射: MapStruct 1.6.3
- 数据库: MySQL 8.0
