package com.concord.petmily.domain.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {

    private Long userId;
    private Long postId;

}
