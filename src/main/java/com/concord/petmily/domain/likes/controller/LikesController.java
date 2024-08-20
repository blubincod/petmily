package com.concord.petmily.likes.controller;

import com.concord.petmily.likes.dto.LikesDto;
import com.concord.petmily.likes.service.LikesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/posts/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesServiceImpl likesServiceImpl;

    /**
     * 좋아요
     * @param dto 등록할 좋아요의 정보
     * @return HTTP 201 상태를 반환
     */
    @PostMapping
    public ResponseEntity<?> createLikes(@RequestBody LikesDto dto) {
        likesServiceImpl.createLikes(dto);
        return ResponseEntity.status(201).body("좋아요 성공");
    }

    /**
     * 좋아요 취소
     * @param dto 취소할 좋아요의 정보
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping
    public ResponseEntity<?> deleteLikes(@RequestBody LikesDto dto) {
        likesServiceImpl.deleteLikes(dto);
        return ResponseEntity.status(204).body(null);
    }

}
