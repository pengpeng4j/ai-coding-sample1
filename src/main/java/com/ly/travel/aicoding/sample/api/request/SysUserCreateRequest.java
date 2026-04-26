package com.ly.travel.aicoding.sample.api.request;

import com.ly.travel.aicoding.common.model.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户创建请求DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:14
 */
@Getter
@Setter
@ToString
public class SysUserCreateRequest extends BaseRequest {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "手机号不能为空")
    private String userPhone;

    private String userEmail;

    private Integer userType;

    private String userAvatar;

}
