package com.concord.petmily.domain.walk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class StartWalkDto {
    @Getter
    @Setter
    public static class Request {
        private List<Long> petIds;
        private LocalDateTime startTime;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long walkId;
        private LocalDateTime startTime;
        private List<Long> pets;
    }
}
