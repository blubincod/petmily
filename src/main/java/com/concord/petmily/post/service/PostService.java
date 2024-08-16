package com.concord.petmily.post.service;

import com.concord.petmily.post.dto.PostDto;
import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.entity.PostCategory;
import com.concord.petmily.post.entity.PostStatus;
import com.concord.petmily.post.repository.PostCategoryRepository;
import com.concord.petmily.post.repository.PostRepository;
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
//                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

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

    @Transactional(readOnly = true)
    public PostDto.Response getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response updatePost(Long postId, PostDto.Request dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new RuntimeException("해당 게시글이 이미 삭제되었습니다.");
        }

        PostCategory postCategory = postCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        post.setPostCategory(postCategory);
        post.setThumbnailPath(dto.getThumbnailPath());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImagePath(dto.getImagePath());
        post.setPostStatus(dto.getPostStatus());
        post.setAttachmentPaths(dto.getAttachmentPaths());

        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        if(post.getPostStatus() == PostStatus.DELETED) {
            throw new RuntimeException("해당 게시글이 이미 삭제되었습니다.");
        }
        post.setPostStatus(PostStatus.DELETED);

        return new PostDto.Response(post);
    }

}