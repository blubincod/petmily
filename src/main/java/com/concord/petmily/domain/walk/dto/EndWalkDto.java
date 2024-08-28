package com.concord.petmily.domain.walk.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class EndWalkDto {
    @Getter
    @Setter
    public static class Request {
        private Long walkId;
        private LocalDateTime endTime;
        private double distance;
    }

    public static class Response {
        private Long walkId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private double distance;
        private long duration;
        private List<Long> petIds;
    }
}