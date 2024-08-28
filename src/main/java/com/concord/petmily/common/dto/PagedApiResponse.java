package com.concord.petmily.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

// API 응답의 표준 형식을 정의
@Getter
@Setter
public class PagedApiResponse<T> {
    private String status;
//    private String message;
    private T data;
    private Pagination pagination;

    public static <T> PagedApiResponse<List<T>> success(Page<T> page) {
        PagedApiResponse<List<T>> response = new PagedApiResponse<>();
        response.setStatus("success");
        response.setData(page.getContent());
        response.setPagination(new Pagination(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        ));
        return response;
    }
}
