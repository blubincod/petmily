package com.concord.petmily.post.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.post.entity.*;
import com.concord.petmily.post.repository.*;
import com.concord.petmily.post.dto.PostDto;
import com.concord.petmily.post.exception.PostException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 게시물 서비스
 * 비즈니스 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostViewRepository postViewRepository;
    private final HashtagRepository hashtagRepository;
    private final PostHashtagRepository postHashtagRepository;

    @Override
    @Transactional
    public PostDto.Response createPost(PostDto.Request dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND);

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_CATEGORY_NOT_FOUND));

        Post post = dto.toEntity();
        post.setPostCategory(postCategory);

        PostDto.Response response = saveHashtags(post, dto);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ResponseGetPosts> getPosts(Long categoryId, String hashtagName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (categoryId == null && hashtagName == null) {
            // categoryId, hashtagName 모두 입력하지 않은 경우
            return postRepository.findAll(pageable).map(PostDto.ResponseGetPosts::new);

        } else if (categoryId == null) {
            // categoryId 입력하지 않은 경우
            Set<Long> postIds = searchPostIds(hashtagName);
            return postRepository.findByIdIn(postIds, pageable).map(PostDto.ResponseGetPosts::new);

        } else if (hashtagName == null) {
            // hashtagName 입력하지 않은 경우
            return postRepository.findByPostCategoryId(categoryId, pageable).map(PostDto.ResponseGetPosts::new);

        } else {
            // categoryId, hashtagName 모두 입력한 경우
            Set<Long> postIds = searchPostIds(hashtagName);
            return postRepository.findByPostCategoryIdAndIdIn(categoryId, postIds, pageable).map(PostDto.ResponseGetPosts::new);
        }
    }

    @Override
    @Transactional
    public PostDto.Response getPost(Long postId, HttpServletRequest req, HttpServletResponse res) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        Optional<PostView> existingRecord = postViewRepository.findByUserIdAndPostId(1L, postId);
        // 조회한 기록이 없는 경우
        if (existingRecord.isEmpty()) {
            // 새 조회 기록 저장 후 조회수 + 1
            PostView newRecord = new PostView();
            newRecord.setUserId(1L);
            newRecord.setPost(post);
            newRecord.setViewTimestamp(System.currentTimeMillis());
            postViewRepository.save(newRecord);
            viewCountUp(postId);
        }

        PostDto.Response response = new PostDto.Response(post);

        // 해시태그 조회
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        Set<Long> hashtagIds = postHashtags.stream()
                .map(postHashtag -> postHashtag.getHashtag().getId())
                .collect(Collectors.toSet());
        Set<String> hashtagNames = hashtagIds.stream()
                .map(id -> hashtagRepository.findById(id)
                        .map(Hashtag::getHashtagName)
                        .orElse(null))
                .collect(Collectors.toSet());
        response.setHashtagNames(hashtagNames);

        return response;
    }

    @Override
    @Transactional
    public PostDto.Response updatePost(Long postId, PostDto.Request dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new PostException(ErrorCode.POST_ALREADY_DELETED);
        }

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_CATEGORY_NOT_FOUND));

        post.setPostCategory(postCategory);
        post.setThumbnailPath(dto.getThumbnailPath());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImagePath(dto.getImagePath());
        post.setPostStatus(dto.getPostStatus());

        // 게시물 수정시 post_hashtag 테이블의 post_id 컬럼을 null로 변경
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        for (PostHashtag postHashtag : postHashtags) {
            postHashtag.setPost(null);
        }
        postHashtagRepository.saveAll(postHashtags);

        PostDto.Response response = saveHashtags(post, dto);
        return response;
    }

    @Override
    @Transactional
    public PostDto.Response deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new PostException(ErrorCode.POST_ALREADY_DELETED);
        }
        post.setPostStatus(PostStatus.DELETED);

        // 게시물 삭제시 post_hashtag 테이블의 post_id 컬럼을 null로 변경
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByPostId(post.getId());
        for (PostHashtag postHashtag : postHashtags) {
            postHashtag.setPost(null);
        }
        postHashtagRepository.saveAll(postHashtags);

        return new PostDto.Response(post);
    }

    @Override
    @Transactional
    public Set<String> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        if(hashtags.isEmpty()) {
            throw new PostException(ErrorCode.HASHTAG_NOT_FOUND);
        }

        return hashtags.stream().map(Hashtag::getHashtagName).collect(Collectors.toSet());
    }

    // 게시물 조회수 증가
    public void viewCountUp(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        post.viewCountUp(post);
    }

    // 새로운 hashtag 등록
    public PostDto.Response saveHashtags(Post post, PostDto.Request dto) {
        for(String hashtagName : dto.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName)
                    .orElseGet(() -> hashtagRepository.save(new Hashtag(hashtagName)));
            postHashtagRepository.save(new PostHashtag(post, hashtag));
        }
        postRepository.save(post);

        PostDto.Response response = new PostDto.Response(post);
        response.setHashtagNames(dto.getHashtagNames());

        return response;
    }

    // hashtagName으로 PostId 찾기
    public Set<Long> searchPostIds(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByHashtagName(hashtagName)
                .orElseThrow(() -> new PostException(ErrorCode.HASHTAG_NOT_FOUND));
        Set<PostHashtag> postHashtags = postHashtagRepository.findAllByHashtagId(hashtag.getId());
        Set<Long> postIds = postHashtags.stream()
                .map(postHashtag -> postHashtag.getPost().getId())
                .collect(Collectors.toSet());

        if (postIds.isEmpty()) {
            // 해당 해시태그로 관련된 게시물이 없는 경우
            throw new PostException(ErrorCode.POST_NOT_FOUND);
        }
        return postIds;
    }
}