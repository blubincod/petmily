package com.concord.petmily.domain.post.service;

import com.concord.petmily.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PostService {
    // 게시물 등록
    PostDto.Response createPost(String username, PostDto.Request dto, List<MultipartFile> files);
    // 게시물 전체 조회
    Page<PostDto.ResponseGetPosts> getPosts(Long categoryId, String hashtagName, Pageable pageable);
    // 게시물 정보 조회
    PostDto.Response getPost(String username, Long postId);
    // 게시물 수정
    PostDto.Response updatePost(String username, Long postId, PostDto.Request dto, List<MultipartFile> files);
    // 게시물 삭제
    void deletePost(String username, Long postId);
    // 전체 해시태그 조회
    Set<String> getAllHashtags();
    // 해시태그 추가
    List<String> createHashtags(String username, List<String> hashtagNames);
    // 게시물 카테고리 변경
    PostDto.Response updateCategory(String username, Long postId, Long categoryId);
}