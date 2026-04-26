package com.ly.travel.aicoding.common.model;

/**
 * 默认响应DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 12:05
 */
public class DefaultResponse<T> {

    protected boolean success;
    protected String code;
    protected String message;
    protected T data;

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> Builder<T> ok(T data) {
        Builder<T> builder = new Builder<>(true);
        return builder.data(data);
    }

    public static <T> Builder<T> error(String code, String message) {
        Builder<T> builder = new Builder<>(false);
        return builder.code(code).message(message);
    }

    public static class Builder<T> {
        private boolean success;
        private String code = "200";
        private String message;
        private T data;

        Builder(boolean success) {
            this.success = success;
        }

        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public DefaultResponse<T> build() {
            DefaultResponse<T> response = new DefaultResponse<>();
            response.success = this.success;
            response.code = this.code;
            response.message = this.message;
            response.data = this.data;
            return response;
        }
    }

}
