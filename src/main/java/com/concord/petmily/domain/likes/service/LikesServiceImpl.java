package com.concord.petmily.domain.likes.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.domain.likes.entity.Likes;
import com.concord.petmily.domain.likes.exception.LikesException;
import com.concord.petmily.domain.likes.repository.LikesRepository;
import com.concord.petmily.domain.notification.entity.Notification;
import com.concord.petmily.domain.notification.service.NotificationService;
import com.concord.petmily.domain.post.entity.Post;
import com.concord.petmily.domain.post.exception.PostException;
import com.concord.petmily.domain.post.repository.PostRepository;
import com.concord.petmily.domain.user.entity.User;
import com.concord.petmily.domain.user.exception.UserNotFoundException;
import com.concord.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
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

    @Override
    public void createLikes(String username, Long postId) {
        User user = getUser(username);

        Post post = getPost(postId);

        // 이미 좋아요 되어있으면 에러
        if (likesRepository.findByUserIdAndPost(user.getId(), post).isPresent()) {
            throw new LikesException(ErrorCode.LIKES_ALREADY_EXISTS);
        }

        Likes likes = Likes.builder()
                .post(post)
                .user(user)
                .build();

        likesRepository.save(likes);
        post.likeCountUp(post);
        postRepository.save(post);

        // 작성자에게 알림 전송
        notificationService.send(post.getUser(), Notification.NotificationType.LIKE, "작성한 게시물에 좋아요가 추가되었습니다.");
    }

    @Override
    public void deleteLikes(String username, Long postId) {
        User user = getUser(username);

        Post post = getPost(postId);

        // 좋아요 되어있지 않으면 에러
        Likes likes = likesRepository.findByUserIdAndPost(user.getId(), post)
                .orElseThrow(() -> new LikesException(ErrorCode.LIKES_NOT_FOUND));

        likesRepository.delete(likes);
        post.likeCountDown(post);
        postRepository.save(post);
    }

}
