package com.concord.petmily.domain.comment.service;

import com.concord.petmily.domain.comment.dto.CommentDto;

public interface CommentService {
    void createComment(String username, Long postId, CommentDto dto);
    void updateComment(String username, Long commentId, CommentDto dto);
    void deleteComment(String username, Long commentId);
}