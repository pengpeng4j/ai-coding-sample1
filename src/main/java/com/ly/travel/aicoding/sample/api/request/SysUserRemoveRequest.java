package com.ly.travel.aicoding.sample.api.request;

import com.ly.travel.aicoding.common.model.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户删除请求DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:37
 */
@Getter
@Setter
@ToString
public class SysUserRemoveRequest extends BaseRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long id;

}
