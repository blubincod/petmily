package com.concord.petmily.comment.service;

import com.concord.petmily.comment.dto.CommentDto;
import com.concord.petmily.comment.entity.Comment;
import com.concord.petmily.comment.exception.CommentException;
import com.concord.petmily.comment.repository.CommentRepository;
import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.exception.PostException;
import com.concord.petmily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Comment createComment(Long postId, CommentDto.Request dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        Comment comment = dto.toEntity();

        // 부모댓글이 있다면, 대댓글
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

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.getIsDeleted()){
            throw new CommentException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        comment.setIsDeleted(true);
        comment.setContent("댓글이 삭제되었습니다.");
    }

    @Override
    public void updateComment(Long commentId, CommentDto.Request dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        // 댓글 수정은 작성자만 가능
        if(!Objects.equals(comment.getUserId(), dto.getUserId())) {
            throw new CommentException(ErrorCode.USER_COMMENT_UNMATCHED);
        }

        comment.setContent(dto.getContent());
    }
}
