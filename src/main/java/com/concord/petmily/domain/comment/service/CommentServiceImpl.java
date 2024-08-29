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
import com.concord.petmily.domain.user.exception.UserNotFoundException;
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

    // 회원 정보 조회
    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 게시물 정보 조회
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
    }

    // 댓글 정보 조회
    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createComment(String username, Long postId, CommentDto dto) {
        User user = getUser(username);

        Post post = getPost(postId);

        Comment comment = dto.toEntity();

        // 부모댓글이 있다면, 대댓글
        Comment parentComment;
        if (dto.getParentId() != null) {
            parentComment = getComment(dto.getParentId());

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
        User user = getUser(username);

        Comment comment = getComment(commentId);
        validateComment(comment);

        // 사용자와 댓글 수정자가 다르면 에러
        validateCommentUser(user, comment);

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String username, Long commentId) {
        User user = getUser(username);

        Comment comment = getComment(commentId);
        validateComment(comment);

        if(user.getRole() != Role.ADMIN) {
            // 사용자가 관리자가 아니라면
            // 사용자와 댓글 삭제자가 다르면 에러
            validateCommentUser(user, comment);
        }

        comment.setIsDeleted(true);
        comment.setContent("댓글이 삭제되었습니다.");
        commentRepository.save(comment);
    }

    // 삭제된 댓글인지 유효성 검사
    private void validateComment(Comment comment) {
        if(comment.getIsDeleted()){
            throw new CommentException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
    }

    // 사용자와 댓글 작성자가 같은지 검사
    private void validateCommentUser(User user, Comment comment) {
        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new CommentException(ErrorCode.USER_COMMENT_UNMATCHED);
        }
    }

}
