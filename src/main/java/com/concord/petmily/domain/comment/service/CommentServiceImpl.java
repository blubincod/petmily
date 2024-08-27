package com.concord.petmily.domain.comment.service;

import com.concord.petmily.domain.comment.dto.CommentDto;
import com.concord.petmily.domain.comment.entity.Comment;
import com.concord.petmily.domain.comment.exception.CommentException;
import com.concord.petmily.domain.comment.repository.CommentRepository;
import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.notification.service.NotificationService;
import com.concord.petmily.domain.post.entity.Post;
import com.concord.petmily.domain.post.exception.PostException;
import com.concord.petmily.domain.post.repository.PostRepository;
import com.concord.petmily.domain.user.entity.Role;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void createComment(String username, Long postId, CommentDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

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

        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);

        // 작성자에게 알림 전송
        notificationService.send(post.getUser(), Notification.NotificationType.COMMENT,"작성한 게시물에 댓글이 작성되었습니다.");
    }

    @Override
    public void updateComment(String username, Long commentId, CommentDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        // 사용자와 댓글 수정자가 다르면 에러
        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new CommentException(ErrorCode.USER_COMMENT_UNMATCHED);
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String username, Long commentId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));

        // 이미 삭제된 댓글이라면 에러
        if(comment.getIsDeleted()){
            throw new CommentException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        if(user.getRole() != Role.ADMIN) {
            // 사용자가 관리자가 아니라면
            // 사용자와 댓글 삭제자가 다르면 에러
            if (!Objects.equals(user.getId(), comment.getUser().getId())) {
                throw new CommentException(ErrorCode.USER_COMMENT_UNMATCHED);
            }
        }

        comment.setIsDeleted(true);
        comment.setContent("댓글이 삭제되었습니다.");
        commentRepository.save(comment);
    }

}
