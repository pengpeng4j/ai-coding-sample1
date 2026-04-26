package com.ly.travel.aicoding.common.model;

import java.util.List;

/**
 * 分页响应DTO
 *
 * @author CLAUDE-CODE
 * @version 1.0.0
 * @date 2026/4/26 12:09
 */
public class PageResponse<T> extends DefaultResponse<List<T>> {

    private long total;

    public long getTotal() {
        return total;
    }

    public List<T> getDataList() {
        return super.getData();
    }

    public static <T> PageResponse.Builder<T> ok(List<T> data, long total) {
        Builder<T> builder = new Builder<>(true);
        return builder.data(data).total(total);
    }

    public static <T> PageResponse.Builder<T> pageError(String code, String message) {
        Builder<T> builder = new Builder<>(false);
        return builder.code(code).message(message);
    }

    public static class Builder<T> {
        private boolean success;
        private String code = "200";
        private String message;
        private List<T> data;
        private long total;

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

        public Builder<T> data(List<T> data) {
            this.data = data;
            return this;
        }

        public Builder<T> total(long total) {
            this.total = total;
            return this;
        }

        public PageResponse<T> build() {
            PageResponse<T> response = new PageResponse<>();
            response.success = this.success;
            response.code = this.code;
            response.message = this.message;
            response.data = this.data;
            response.total = this.total;
            return response;
        }
    }

}
