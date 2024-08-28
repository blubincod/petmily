package com.concord.petmily.domain.post.controller;

import com.concord.petmily.common.dto.ApiResponse;
import com.concord.petmily.common.dto.PagedApiResponse;
import com.concord.petmily.domain.post.dto.PostDto;
import com.concord.petmily.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * 게시물 관련 컨트롤러
 *
 * - 게시물 등록
 * - 게시물 조회
 * - 게시물 수정
 * - 게시물 삭제
 * - 해시태그 조회
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    /**
     * 새로운 게시물 등록
     * @param dto 등록할 게시물의 정보
     * @param files 등록할 이미지파일들
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return 생성된 게시물의 정보와 HTTP 201 상태를 반환
     */
    @PostMapping
    public ResponseEntity<PostDto.Response> createPost (@RequestPart PostDto.Request dto,
                                                        @RequestPart(required = false) List<MultipartFile> files,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        PostDto.Response response = postService.createPost(username, dto, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 게시물 전체 조회 (카테고리별, 해시태그별)
     * @param categoryId 조회할 카테고리 id
     * @param hashtagName 조회할 해시태그이름
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping
    public ResponseEntity<PagedApiResponse<List<PostDto.ResponseGetPosts>>> getPosts (@RequestParam(required = false) Long categoryId,
                                                                                      @RequestParam(required = false) String hashtagName,
                                                                                      Pageable pageable) {
        Page<PostDto.ResponseGetPosts> responses = postService.getPosts(categoryId, hashtagName, pageable);

        return ResponseEntity.ok(PagedApiResponse.success(responses));
    }

    /**
     * 게시물 정보 조회
     * @param postId 조회할 게시물의 ID
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return 조회된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.Response> getPost (@PathVariable Long postId,
                                                     @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        PostDto.Response response = postService.getPost(username, postId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 게시물 수정
     * @param postId 수정할 게시물의 ID
     * @param dto 수정할 게시물의 정보
     * @param files 수정할 이미지파일들
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return 수정된 게시물의 정보와 HTTP 200 상태를 반환
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto.Response> updatePost (@PathVariable Long postId,
                                                        @RequestPart PostDto.Request dto,
                                                        @RequestPart(required = false) List<MultipartFile> files,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        PostDto.Response response = postService.updatePost(username, postId, dto, files);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 게시물 삭제
     * @param postId 삭제할 게시물의 ID
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return HTTP 204 상태를 반환
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost (@PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        postService.deletePost(username, postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * 전체 해시태그 조회
     * @return 전체 해시태그의 정보와 HTTP 200 상태를 반환
     */
    @GetMapping("/hashtags")
    public ResponseEntity<Set<String>> getAllHashtags (){
        Set<String> hashtagNames = postService.getAllHashtags();

        return ResponseEntity.status(HttpStatus.OK).body(hashtagNames);
    }

    /**
     * 해시태그 추가(관리자만 가능)
     * @param hashtagNames 추가할 해시태그
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @return 생성된 해시태그와 HTTP 201 상태를 반환
     */
    @PostMapping("/hashtags")
    public ResponseEntity<List<String>> createHashtags (@RequestBody List<String> hashtagNames,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        List<String> hashtags = postService.createHashtags(username, hashtagNames);

        return ResponseEntity.status(HttpStatus.CREATED).body(hashtags);
    }

    /**
     * 게시물 카테고리 변경(관리자만 가능)
     * @param postId 변경할 게시물
     * @param categoryId 변경될 카테고리
     * @param userDetails 현재 인증된 사용자의 세부 정보
     */
    @PutMapping("/category/{postId}")
    public ResponseEntity<PostDto.Response> updateCategory (@PathVariable Long postId,
                                                            @RequestBody Long categoryId,
                                                            @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        PostDto.Response response = postService.updateCategory(username, postId, categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}