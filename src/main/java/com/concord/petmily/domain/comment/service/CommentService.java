package com.concord.petmily.domain.comment.service;

import com.concord.petmily.domain.comment.dto.CommentDto;

public interface CommentService {
    // 댓글 등록
    void createComment(String username, Long postId, CommentDto dto);
    // 댓글 수정
    void updateComment(String username, Long commentId, CommentDto dto);
    // 댓글 삭제
    void deleteComment(String username, Long commentId);
}