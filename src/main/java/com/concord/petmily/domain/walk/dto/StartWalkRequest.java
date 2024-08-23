package com.concord.petmily.domain.walk.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StartWalkRequest {
    private List<Long> petIds;
    private LocalDateTime startTime;
}
