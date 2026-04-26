package com.ly.travel.aicoding.sample.biz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.travel.aicoding.common.model.PageResponse;
import com.ly.travel.aicoding.common.support.AbstractManageService;
import com.ly.travel.aicoding.sample.api.model.SysUserDTO;
import com.ly.travel.aicoding.sample.api.request.*;
import com.ly.travel.aicoding.sample.biz.service.SysUserManageService;
import com.ly.travel.aicoding.sample.domain.model.SysUserDO;
import com.ly.travel.aicoding.sample.domain.model.SysUserQuery;
import com.ly.travel.aicoding.sample.domain.service.SysUserService;
import com.ly.travel.aicoding.sample.mapping.SysUserMapping;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 系统用户管理服务实现
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:59
 */
@Service
public class SysUserManageServiceImpl extends AbstractManageService implements SysUserManageService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapping sysUserMapping;

    @Override
    public SysUserDTO createUser(SysUserCreateRequest request) {
        SysUserDO exist = sysUserService.selectUserByName(request.getUserName());
        if (exist != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        exist = sysUserService.selectUserByPhone(request.getUserPhone());
        if (exist != null) {
            throw new IllegalArgumentException("手机号已存在");
        }

        SysUserDO user = sysUserMapping.toCreateDO(request);
        if (user.getUserType() == null) {
            user.setUserType(0);
        }
        if (user.getUserStatus() == null) {
            user.setUserStatus(1);
        }
        String operator = getOperator();
        user.setCreateBy(operator);
        user.setUpdateBy(operator);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        sysUserService.insertUser(user);
        return sysUserMapping.toDTO(user);
    }

    @Override
    public SysUserDTO modifyUserById(SysUserModifyRequest request) {
        SysUserDO user = sysUserService.selectUserById(request.getId());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (user.getUserStatus() == 2) {
            throw new IllegalArgumentException("用户已删除，不可修改");
        }

        if (request.getUserName() != null && !request.getUserName().equals(user.getUserName())) {
            SysUserDO exist = sysUserService.selectUserByName(request.getUserName());
            if (exist != null) {
                throw new IllegalArgumentException("用户名已存在");
            }
        }
        if (request.getUserPhone() != null && !request.getUserPhone().equals(user.getUserPhone())) {
            SysUserDO exist = sysUserService.selectUserByPhone(request.getUserPhone());
            if (exist != null) {
                throw new IllegalArgumentException("手机号已存在");
            }
        }

        SysUserDO update = sysUserMapping.toModifyDO(request);
        update.setUpdateBy(getOperator());
        update.setUpdateTime(LocalDateTime.now());
        sysUserService.updateUserById(update);

        return sysUserMapping.toDTO(sysUserService.selectUserById(request.getId()));
    }

    @Override
    public Boolean changeStatusById(SysUserStatusRequest request) {
        SysUserDO user = sysUserService.selectUserById(request.getId());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (request.getUserStatus() < 0 || request.getUserStatus() > 2) {
            throw new IllegalArgumentException("状态值非法");
        }
        sysUserService.changeUserStatusById(request.getId(), request.getUserStatus());
        return true;
    }

    @Override
    public Boolean removeUserById(SysUserRemoveRequest request) {
        SysUserDO user = sysUserService.selectUserById(request.getId());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (user.getUserStatus() == 2) {
            throw new IllegalArgumentException("用户已删除");
        }
        sysUserService.deleteUserById(request.getId());
        return true;
    }

    @Override
    public SysUserDTO queryUserById(SysUserDetailRequest request) {
        SysUserDO user = sysUserService.selectUserById(request.getId());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (user.getUserStatus() == 2) {
            throw new IllegalArgumentException("用户已删除");
        }
        return sysUserMapping.toDTO(user);
    }

    @Override
    public PageResponse<SysUserDTO> queryUserByPage(SysUserPageRequest request) {
        SysUserQuery query = sysUserMapping.toQuery(request);
        Page<SysUserDO> page = new Page<>(request.getPageNum(), request.getPageSize());

        Page<SysUserDO> result = sysUserService.selectUserByPage(query, page);
        return PageResponse.ok(result.getRecords().stream().map(sysUserMapping::toDTO).toList(), result.getTotal()).build();
    }

}
