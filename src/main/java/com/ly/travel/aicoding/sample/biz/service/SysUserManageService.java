package com.ly.travel.aicoding.sample.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.*;

/**
 * 系统用户管理服务
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:59
 */
public interface SysUserManageService {

    SysUserDTO createUser(SysUserCreateRequest request);

    SysUserDTO modifyUserById(SysUserModifyRequest request);

    Boolean changeStatusById(SysUserStatusRequest request);

    Boolean removeUserById(SysUserRemoveRequest request);

    SysUserDTO queryUserById(SysUserDetailRequest request);

    Page<SysUserDTO> queryUserByPage(SysUserPageRequest request);

}
