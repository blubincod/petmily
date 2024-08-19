package com.concord.petmily.comment.controller;

import com.concord.petmily.comment.dto.CommentDto;
import com.concord.petmily.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 관련 컨트롤러
 *
 * - 댓글 등록
 * - 댓글 수정
 * - 댓글 삭제
 */

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글 등록
     * @param postId 댓글이 작성될 게시물
     * @param dto 등록할 댓글의 정보
     * @return HTTP 201 상태를 반환
     */
    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> insert (@PathVariable Long postId,
                                     @RequestBody CommentDto.Request dto) {
        commentService.insert(postId, dto);

        return ResponseEntity.status(201).body("댓글 등록 성공");
    }

    /**
     * 댓글 수정
     * @param commentId 수정될 댓글의 id
     * @param dto 등록할 댓글의 정보
     * @return HTTP 200 상태를 반환
     */
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> update (@PathVariable Long commentId, @RequestBody CommentDto.Request dto) {
        commentService.update(commentId, dto);

        return ResponseEntity.status(200).body("댓글 수정 성공");
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제될 댓글의 id
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> delete (@PathVariable Long commentId) {
        commentService.delete(commentId);

        return ResponseEntity.status(204).body(null);
    }

}