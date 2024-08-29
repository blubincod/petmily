package com.concord.petmily.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public Pagination(long totalItems, int totalPages, int currentPage, int pageSize) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
