package com.ly.travel.aicoding.sample.api.controller;

import com.ly.travel.aicoding.common.model.DefaultResponse;
import com.ly.travel.aicoding.common.model.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * 接口基类
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 16:08
 */
public abstract class AbstractController {

    protected <T> DefaultResponse<T> defaultResponse(String code, String message) {
        return DefaultResponse.<T>error(code, message).build();
    }

    protected <T> DefaultResponse<T> defaultResponse(T data) {
        return DefaultResponse.ok(data).build();
    }

    protected <T> DefaultResponse<T> defaultResponse(boolean success, String code, String message, T data) {
        if (success) {
            return DefaultResponse.<T>ok(data).build();
        }
        DefaultResponse.Builder<T> builder = DefaultResponse.error(code, message);
        return builder.build();
    }

    protected <T> PageResponse<T> pageResponse(String code, String message) {
        return PageResponse.<T>pageError(code, message).build();
    }

    protected <T> PageResponse<T> pageResponse(List<T> dataList, long total) {
        return PageResponse.<T>ok(dataList, total).build();
    }

    protected <T> PageResponse<T> pageResponse(boolean success, String code, String message, List<T> data, long total) {
        if (success) {
            return PageResponse.<T>ok(data, total).build();
        }
        PageResponse.Builder<T> builder = PageResponse.pageError(code, message);
        return builder.build();
    }

    protected HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }

    protected HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getResponse() : null;
    }

}
