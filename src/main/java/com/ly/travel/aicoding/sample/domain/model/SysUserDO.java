package com.ly.travel.aicoding.sample.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户领域模型
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 17:05
 */
@Getter
@Setter
@ToString
public class SysUserDO implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String userPhone;

    private String userEmail;

    /**
     * 用户类型(0:普通用户, 1:系统用户)
     */
    private Integer userType;

    /**
     * 用户状态(0:禁用, 1:启用, 2:删除)
     */
    private Integer userStatus;

    private String userAvatar;

    //以下是公共字段
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
    private String env;

}
