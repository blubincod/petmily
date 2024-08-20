package com.concord.petmily.domain.comment.service;

import com.concord.petmily.domain.comment.dto.CommentDto;
import com.concord.petmily.domain.comment.entity.Comment;
import com.concord.petmily.domain.comment.exception.CommentException;
import com.concord.petmily.domain.comment.repository.CommentRepository;
import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.post.entity.Post;
import com.concord.petmily.domain.post.exception.PostException;
import com.concord.petmily.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment insert(Long postId, CommentDto.Request dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        Comment comment = dto.toEntity();

        Comment parentComment;
        if (dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setParent(parentComment);
        }

//        comment.setUserId(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.getIsDeleted()){
            throw new CommentException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        comment.setIsDeleted(true);
        comment.setContent("댓글이 삭제되었습니다.");
    }

    @Transactional
    public void update(Long commentId, CommentDto.Request dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.getUserId() != dto.getUserId()) {
            throw new CommentException(ErrorCode.USER_COMMENT_UNMATCHED);
        }

        comment.setContent(dto.getContent());
    }
}
