package com.ly.travel.aicoding.sample.api.request;

import com.ly.travel.aicoding.common.model.PageRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户分页查询请求DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:14
 */
@Getter
@Setter
@ToString
public class SysUserPageRequest extends PageRequest {

    private String userName;

    private Integer userType;

    private Integer userStatus;

}
