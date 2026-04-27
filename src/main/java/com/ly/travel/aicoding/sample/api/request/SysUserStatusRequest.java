package com.ly.travel.aicoding.sample.api.request;

import com.ly.travel.aicoding.common.model.BaseRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户状态变更请求DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:14
 */
@Getter
@Setter
@ToString
public class SysUserStatusRequest extends BaseRequest {

    @NotNull(message = "用户ID不能为空")
    private Long id;

    @NotNull(message = "用户状态不能为空")
    @Max(value = 1, message = "状态值非法")
    private Integer userStatus;

}
