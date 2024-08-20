package com.concord.petmily.comment.service;

import com.concord.petmily.comment.dto.CommentDto;
import com.concord.petmily.comment.entity.Comment;

public interface CommentService {
    Comment createComment(Long postId, CommentDto.Request dto);
    void deleteComment(Long commentId);
    void updateComment(Long commentId, CommentDto.Request dto);
}
