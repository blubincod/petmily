package com.concord.petmily.domain.likes.controller;

import com.concord.petmily.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/posts/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    /**
     * 좋아요
     * @param postId 좋아요 등록할 게시물
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return HTTP 201 상태를 반환
     */
    @PostMapping("/{postId}")
    public ResponseEntity<String> createLikes(@PathVariable Long postId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        likesService.createLikes(username, postId);

        return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 성공");
    }

    /**
     * 좋아요 취소
     * @param postId 좋아요 취소할 게시물
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteLikes(@PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        likesService.deleteLikes(username, postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
