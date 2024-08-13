package com.concord.petmily.post.controller;

import com.concord.petmily.post.dto.PostRequestDTO;
import com.concord.petmily.post.service.PostService;
import lombok.RequiredArgsConstructor;
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
     * @param postRequestDTO 수정할 게시물의 정보
     * @return 생성된 게시물의 정보와 HTTP 201 상태를 반환
     */
    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody PostRequestDTO postRequestDTO){
        postService.createPost();

        return ResponseEntity.status(201).body(null);
    }

    /**
     * 게시물 전체 조회 (카테고리별)
     * @param categoryId 조회할 카테고리 id
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping
    public ResponseEntity<?> getPosts (@RequestParam(required = false) int categoryId){
        postService.getPosts();

        return ResponseEntity.status(200).body(null);
    }

    /**
     * 게시물 정보 조회
     * @param postId 수정할 게시물의 ID
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost (@PathVariable Long postId){
        postService.getPost();

        return ResponseEntity.status(200).body(null);
    }

    /**
     * 게시물 수정
     * @param postId 수정할 게시물의 ID
     * @param postRequestDTO 수정할 게시물의 정보
     * @return 수정된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost (@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO){
        postService.updatePost();

        return ResponseEntity.status(200).body(null);
    }

    /**
     * 게시물 삭제
     * @param postId 수정할 게시물의 ID
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost (@PathVariable Long postId){
        postService.deletePost();

        return ResponseEntity.status(204).body(null);
    }

}
