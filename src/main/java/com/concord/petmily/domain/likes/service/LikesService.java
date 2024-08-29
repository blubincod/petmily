package com.concord.petmily.domain.likes.service;

public interface LikesService {
    // 좋아요 등록
    void createLikes(String username, Long postId);
    // 좋아요 취소
    void deleteLikes(String username, Long postId);
}
