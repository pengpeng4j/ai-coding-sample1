package com.ly.travel.aicoding.sample.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 系统用户DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:14
 */
@Getter
@Setter
@ToString
public class SysUserDTO {

    private Long id;

    private String userName;

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

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

}
