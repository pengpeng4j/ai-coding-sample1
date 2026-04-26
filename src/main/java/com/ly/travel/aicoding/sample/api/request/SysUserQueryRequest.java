package com.ly.travel.aicoding.sample.api.request;

import com.ly.travel.aicoding.common.model.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统用户查询请求DTO
 * (这是一个共用的查询DTO)
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:14
 */
@Getter
@Setter
@ToString
public class SysUserQueryRequest extends BaseRequest {

    /** 用户ID,精确查询 */
    private Long id;

    /** 用户名,精确查询 */
    private String userName;

    /** 用户类型 */
    private Integer userType;

    /** 用户类型 */
    private Integer userStatus;

}
