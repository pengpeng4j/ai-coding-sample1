package com.ly.travel.aicoding.sample.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共的请求DTO基类
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 12:00
 */
@Getter
@Setter
@ToString
public abstract class BaseRequest {

    /**
     * 请求链路追踪ID
     */
    private String traceId;

}
