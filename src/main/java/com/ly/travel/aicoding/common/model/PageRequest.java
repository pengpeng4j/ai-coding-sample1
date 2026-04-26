package com.ly.travel.aicoding.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用分页查询请求DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 15:45
 */
@Getter
@Setter
@ToString
public class PageRequest extends BaseRequest {

    /** 当前页码，默认为1 */
    private Integer pageNum = 1;

    /** 每页条数，默认为10 */
    private Integer pageSize = 10;

}
