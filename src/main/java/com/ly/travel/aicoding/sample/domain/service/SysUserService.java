package com.ly.travel.aicoding.sample.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.travel.aicoding.sample.dal.mapper.SysUserMapper;
import com.ly.travel.aicoding.sample.domain.model.SysUserDO;
import com.ly.travel.aicoding.sample.domain.model.SysUserQuery;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统用户领域服务
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:11
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public int insertUser(SysUserDO user) {
        return sysUserMapper.insert(user);
    }

    public int updateUserById(SysUserDO user) {
        return sysUserMapper.updateById(user);
    }

    public int changeUserStatusById(Long id, Integer userStatus) {
        SysUserDO user = new SysUserDO();
        user.setId(id);
        user.setUserStatus(userStatus);
        return sysUserMapper.updateById(user);
    }

    public int deleteUserById(Long id) {
        SysUserDO user = new SysUserDO();
        user.setId(id);
        user.setUserStatus(2);
        return sysUserMapper.updateById(user);
    }

    public SysUserDO selectUserById(Long id) {
        return sysUserMapper.selectById(id);
    }

    public Page<SysUserDO> selectUserByPage(SysUserQuery query, Page<SysUserDO> page) {
        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(SysUserDO::getUserStatus, 2);

        if (Objects.nonNull(query)) {
            wrapper.eq(StringUtils.isNotBlank(query.getUserName()), SysUserDO::getUserName, query.getUserName());
            wrapper.eq(Objects.nonNull(query.getUserType()), SysUserDO::getUserType, query.getUserType());
            wrapper.eq(Objects.nonNull(query.getUserStatus()), SysUserDO::getUserStatus, query.getUserStatus());
            wrapper.in(CollectionUtils.isNotEmpty(query.getUserStatuses()), SysUserDO::getUserStatus, query.getUserStatuses());
        }

        wrapper.orderByDesc(SysUserDO::getCreateTime);
        return sysUserMapper.selectPage(page, wrapper);
    }

    public SysUserDO selectUserByName(String userName) {
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserDO>()
                .eq(SysUserDO::getUserName, userName));
    }

    public SysUserDO selectUserByPhone(String userPhone) {
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserDO>()
                .eq(SysUserDO::getUserPhone, userPhone));
    }

}
