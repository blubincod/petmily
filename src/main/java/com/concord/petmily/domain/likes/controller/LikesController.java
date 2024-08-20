package com.concord.petmily.domain.likes.controller;

import com.concord.petmily.domain.likes.dto.LikesDto;
import com.concord.petmily.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/posts/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    /**
     * 좋아요
     * @param dto 등록할 좋아요의 정보
     * @return HTTP 201 상태를 반환
     */
    @PostMapping
    public ResponseEntity<?> insert (@RequestBody LikesDto dto) throws Exception {
        likesService.insert(dto);
        return ResponseEntity.status(201).body("좋아요 성공");
    }

    /**
     * 좋아요 취소
     * @param dto 취소할 좋아요의 정보
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping
    public ResponseEntity<?> delete (@RequestBody LikesDto dto) {
        likesService.delete(dto);
        return ResponseEntity.status(204).body(null);
    }

}
