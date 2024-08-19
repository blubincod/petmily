package com.concord.petmily.post.service;

import com.concord.petmily.common.exception.ErrorCode;
import com.concord.petmily.post.dto.PostDto;
import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.entity.PostCategory;
import com.concord.petmily.post.entity.PostStatus;
import com.concord.petmily.post.exception.PostException;
import com.concord.petmily.post.repository.PostCategoryRepository;
import com.concord.petmily.post.repository.PostRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시물 서비스
 * 비즈니스 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Transactional
    public PostDto.Response createPost(PostDto.Request dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND);

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new PostException(ErrorCode.POST_CATEGORY_NOT_FOUND));

        Post post = dto.toEntity();
        post.setPostCategory(postCategory);
        post.setViewCount(0);
        post.setLikeCount(0);
        postRepository.save(post);

        return new PostDto.Response(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto.Response> getPosts(Long categoryId, int page, int size) {
        if (categoryId == null) {
            Pageable pageable = PageRequest.of(page, size);
            return postRepository.findAll(pageable).map(PostDto.Response::new);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            return postRepository.findByPostCategoryId(categoryId, pageable).map(PostDto.Response::new);
        }
    }

    @Transactional
    public PostDto.Response getPost(Long postId, HttpServletRequest req, HttpServletResponse res) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        // 조회수 중복 방지 (쿠키 유효시간 24시간)
        Cookie oldCookie = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ postId.toString() +"]")) {
                viewCountUp(postId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            viewCountUp(postId);
            Cookie newCookie = new Cookie("postView", "[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }

        return new PostDto.Response(post);
    }

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

        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new PostException(ErrorCode.POST_ALREADY_DELETED);
        }
        post.setPostStatus(PostStatus.DELETED);

        return new PostDto.Response(post);
    }

    public void viewCountUp(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));
        post.viewCountUp(post);
    }

}