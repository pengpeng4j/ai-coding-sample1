package com.ly.travel.aicoding.sample.api.controller;

import com.ly.travel.aicoding.common.model.DefaultResponse;
import com.ly.travel.aicoding.common.model.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
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
        return defaultResponse(false, code, message, null);
    }

    protected <T> DefaultResponse<T> defaultResponse(T data) {
        //TODO
        return null;
    }

    protected <T> DefaultResponse<T> defaultResponse(boolean success, String code, String message, T data) {
        //TODO
        return null;
    }

    protected <T> PageResponse<T> pageResponse(String code, String message) {
        return pageResponse(false, code, message, Collections.emptyList(), 0);
    }

    protected <T> PageResponse<T> pageResponse(List<T> dataList, long total) {
        //TODO
        return null;
    }

    protected <T> PageResponse<T> pageResponse(boolean success, String code, String message, List<T> data, long total) {
        //TODO
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(HttpServletRequest request, String key) {
        return (T) request.getAttribute(key);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getSessionAttribute(HttpServletRequest request, String key) {
        return (T) request.getSession().getAttribute(key);
    }

    protected void setRequestAttribute(HttpServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
    }

    protected void setSessionAttribute(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    protected void removeRequestAttribute(HttpServletRequest request, String key) {
        request.removeAttribute(key);
    }

    protected void removeSessionAttribute(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    protected HttpServletRequest getCurrentRequest() {
        //TODO
        return null;
    }

    protected HttpServletResponse getCurrentResponse() {
        //TODO
        return null;
    }

}
