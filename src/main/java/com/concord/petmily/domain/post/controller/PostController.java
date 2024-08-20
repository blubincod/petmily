package com.concord.petmily.domain.post.controller;

import com.concord.petmily.domain.post.dto.PostDto;
import com.concord.petmily.domain.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시물 관련 컨트롤러
 *
 * - 게시물 등록
 * - 게시물 조회
 * - 게시물 수정
 * - 게시물 삭제
 */
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 새로운 게시물 등록
     * @param dto 등록할 게시물의 정보
     * @return 생성된 게시물의 정보와 HTTP 201 상태를 반환
     */
    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody PostDto.Request dto){
        PostDto.Response response = postService.createPost(dto);

        return ResponseEntity.status(201).body(response);
    }

    /**
     * 게시물 전체 조회 (카테고리별)
     * @param categoryId 조회할 카테고리 id
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping
    public ResponseEntity<?> getPosts (@RequestParam(required = false) Long categoryId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size){
        Page<PostDto.Response> responses = postService.getPosts(categoryId, page, size);

        return ResponseEntity.status(200).body(responses);
    }

    /**
     * 게시물 정보 조회
     * @param postId 조회할 게시물의 ID
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost (@PathVariable Long postId,
                                      HttpServletRequest req,
                                      HttpServletResponse res){
        PostDto.Response response = postService.getPost(postId, req, res);

        return ResponseEntity.status(200).body(response);
    }

    /**
     * 게시물 수정
     * @param postId 수정할 게시물의 ID
     * @param dto 수정할 게시물의 정보
     * @return 수정된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost (@PathVariable Long postId, @RequestBody PostDto.Request dto){
        PostDto.Response response = postService.updatePost(postId, dto);

        return ResponseEntity.status(200).body(response);
    }

    /**
     * 게시물 삭제
     * @param postId 삭제할 게시물의 ID
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost (@PathVariable Long postId){
        PostDto.Response response = postService.deletePost(postId);

        return ResponseEntity.status(204).body(response);
    }

}
