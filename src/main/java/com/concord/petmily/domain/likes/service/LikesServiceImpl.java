package com.concord.petmily.likes.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.likes.dto.LikesDto;
import com.concord.petmily.likes.entity.Likes;
import com.concord.petmily.likes.exception.LikesException;
import com.concord.petmily.likes.repository.LikesRepository;
import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.exception.PostException;
import com.concord.petmily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;
//    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void createLikes(LikesDto dto) {

//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        // 이미 좋아요되어있으면 에러 반환
        if (likesRepository.findByUserIdAndPost(dto.getUserId(), post).isPresent()) {
            throw new LikesException(ErrorCode.LIKES_ALREADY_EXISTS);
        }

        Likes likes = Likes.builder()
                .post(post)
                .userId(dto.getUserId())
                .build();

        likesRepository.save(likes);
        post.likeCountUp(post);
    }

    @Override
    public void deleteLikes(LikesDto dto) {

//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        // 좋아요 되어있지 않으면 에러 반환
        Likes likes = likesRepository.findByUserIdAndPost(dto.getUserId(), post)
                .orElseThrow(() -> new LikesException(ErrorCode.LIKES_NOT_FOUND));

        likesRepository.delete(likes);
        post.likeCountDown(post);
    }

}
