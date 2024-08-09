package com.concord.petmily.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class PostController {

    /**
     * 새로운 게시물 등록
     *
     * 예시)
     * @param postId 수정할 게시물의 ID
     * @param postDTO 수정할 게시물의 정보
     * @return 생성된 게시물의 정보와 HTTP 200 상태를 반
     */
    @PostMapping
    public ResponseEntity<?> createPost (){
        // TODO: 게시물 등록 로직 구현
        return ResponseEntity.ok(null);
    }

}
