package com.ly.travel.aicoding.sample.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.travel.aicoding.common.model.DefaultResponse;
import com.ly.travel.aicoding.common.model.PageResponse;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.*;
import com.ly.travel.aicoding.sample.biz.service.SysUserManageService;
import jakarta.annotation.Resource;
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

    @Resource
    private SysUserManageService sysUserManageService;

    /**
     * 创建系统用户
     */
    @PostMapping("/createUser")
    public DefaultResponse<SysUserDTO> createUser(@RequestBody @Validated SysUserCreateRequest request) {
        return defaultResponse(sysUserManageService.createUser(request));
    }

    /**
     * 根据ID修改用户信息
     */
    @PostMapping("/modifyUserById")
    public DefaultResponse<SysUserDTO> modifyUserById(@RequestBody @Validated SysUserModifyRequest request) {
        return defaultResponse(sysUserManageService.modifyUserById(request));
    }

    /**
     * 根据ID修改用户状态
     */
    @PostMapping("/changeStatusById")
    public DefaultResponse<Boolean> changeStatusById(@RequestBody @Validated SysUserStatusRequest request) {
        return defaultResponse(sysUserManageService.changeStatusById(request));
    }

    /**
     * 根据ID删除用户信息
     */
    @PostMapping("/removeUserById")
    public DefaultResponse<Boolean> removeUserById(@RequestBody @Validated SysUserRemoveRequest request) {
        return defaultResponse(sysUserManageService.removeUserById(request));
    }

    /**
     * 根据ID查询用户详情
     */
    @PostMapping("/queryUserById")
    public DefaultResponse<SysUserDTO> queryUserById(@RequestBody @Validated SysUserDetailRequest request) {
        return defaultResponse(sysUserManageService.queryUserById(request));
    }

    /**
     * 分页查询用户列表
     */
    @PostMapping("/queryUserByPage")
    public PageResponse<SysUserDTO> queryUsersByPage(@RequestBody @Validated SysUserPageRequest request) {
        Page<SysUserDTO> page = sysUserManageService.queryUserByPage(request);
        return pageResponse(page.getRecords(), page.getTotal());
    }

}
