package com.concord.petmily.domain.post.service;

import com.concord.petmily.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface PostService {
    PostDto.Response createPost(String username, PostDto.Request dto, List<MultipartFile> files);
    Page<PostDto.ResponseGetPosts> getPosts(Long categoryId, String hashtagName, Pageable pageable);
    PostDto.Response getPost(String username, Long postId);
    PostDto.Response updatePost(String username, Long postId, PostDto.Request dto, List<MultipartFile> files);
    void deletePost(String username, Long postId);
    Set<String> getAllHashtags();
    List<String> createHashtags(String username, List<String> hashtagNames);
}