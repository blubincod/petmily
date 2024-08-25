package com.concord.petmily.common.dto;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Pagination pagination;

    // 생성자, getter, setter 등

    public static class Pagination {
        private int totalItems;
        private int totalPages;
        private int currentPage;
        private int pageSize;

        // 생성자, getter, setter 등
    }
}
