package com.ly.travel.aicoding.sample.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_user")
public class SysUserDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("user_phone")
    private String userPhone;

    @TableField("user_email")
    private String userEmail;

    /**
     * 用户类型(0:普通用户, 1:系统用户)
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 用户状态(0:禁用, 1:启用, 2:删除)
     */
    @TableField("user_status")
    private Integer userStatus;

    @TableField("user_avatar")
    private String userAvatar;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
