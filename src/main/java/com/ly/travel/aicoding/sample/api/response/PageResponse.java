package com.ly.travel.aicoding.sample.api.response;

import java.util.List;

/**
 * 分页响应DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 12:09
 */
public class PageResponse<T> extends DefaultResponse<List<T>> {

    /**
     * 总记录数
     */
    private long totalCount;

}
