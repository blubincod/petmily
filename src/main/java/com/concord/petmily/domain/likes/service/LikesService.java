package com.concord.petmily.domain.likes.service;

public interface LikesService {
    void createLikes(String username, Long postId);
    void deleteLikes(String username, Long postId);
}
