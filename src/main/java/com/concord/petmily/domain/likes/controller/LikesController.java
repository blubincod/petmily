package com.concord.petmily.domain.likes.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요 관련 컨트롤러
 *
 * - 좋아요 등록
 * - 좋아요 삭제
 */
@RestController
@RequestMapping("/api/vi/posts/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    /**
     * 좋아요
     * @param postId 좋아요 등록할 게시물
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> createLikes(@PathVariable Long postId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        likesService.createLikes(username, postId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 좋아요 취소
     * @param postId 좋아요 취소할 게시물
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deleteLikes(@PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        likesService.deleteLikes(username, postId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
