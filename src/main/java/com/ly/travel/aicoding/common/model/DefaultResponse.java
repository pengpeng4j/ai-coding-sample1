package com.ly.travel.aicoding.common.model;

/**
 * 默认响应DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 12:05
 */
public class DefaultResponse<T> {

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 响应代码(同HTTP状态码)
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

}
