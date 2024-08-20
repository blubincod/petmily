package com.concord.petmily.likes.service;

import com.concord.petmily.likes.dto.LikesDto;

public interface LikesService {
    void createLikes(LikesDto dto);
    void deleteLikes(LikesDto dto);
}
