package com.concord.petmily.post.service;

import com.concord.petmily.post.dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface PostService {
    PostDto.Response createPost(PostDto.Request dto);
    Page<PostDto.ResponseGetPosts> getPosts(Long categoryId, String hashtagName, int page, int size);
    PostDto.Response getPost(Long postId, HttpServletRequest req, HttpServletResponse res);
    PostDto.Response updatePost(Long postId, PostDto.Request dto);
    PostDto.Response deletePost(Long postId);
    Set<String> getAllHashtags();
}
