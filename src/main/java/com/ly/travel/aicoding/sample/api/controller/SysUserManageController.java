package com.ly.travel.aicoding.sample.api.controller;

import com.ly.travel.aicoding.common.model.DefaultResponse;
import com.ly.travel.aicoding.common.model.PageResponse;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户管理端接口
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:07
 */
@RestController
@RequestMapping("/manage/sysuser")
public class SysUserManageController extends AbstractController {

    /**
     * 创建系统用户
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/createUser")
    public DefaultResponse<SysUserDTO> createUser(@RequestBody @Validated SysUserCreateRequest request) {
        //TODO
        return null;
    }

    /**
     * 根据ID修改用户信息
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/modifyUserById")
    public DefaultResponse<SysUserDTO> modifyUserById(@RequestBody @Validated SysUserModifyRequest request) {
        //TODO
        return null;
    }

    /**
     * 根据ID修改用户状态
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/changeStatusById")
    public DefaultResponse<Boolean> changeStatusById(@RequestBody @Validated SysUserStatusRequest request) {
        //TODO
        return null;
    }

    /**
     * 根据ID删除用户信息
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/removeUserById")
    public DefaultResponse<Boolean> removeUserById(@RequestBody @Validated SysUserRemoveRequest request) {
        //TODO
        return null;
    }

    /**
     * 根据ID查询用户详情
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/queryUserById")
    public DefaultResponse<SysUserDTO> queryUserById(@RequestBody @Validated SysUserDetailRequest request) {
        //TODO
        return null;
    }

    /**
     * 分页查询用户列表
     *
     * @param request 请求参数
     * @return 响应参数
     */
    @PostMapping("/queryUserByPage")
    public PageResponse<SysUserDTO> queryUserByPage(@RequestBody @Validated SysUserPageRequest request) {
        //TODO
        return null;
    }

}
