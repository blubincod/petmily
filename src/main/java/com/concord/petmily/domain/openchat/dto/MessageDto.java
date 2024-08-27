package com.concord.petmily.domain.openchat.dto;

import java.time.LocalDateTime;

public class MessageDto {
    private Long id;
    private String userId;
    private String nickname;
    private String content;
    private LocalDateTime sentTime;
}
