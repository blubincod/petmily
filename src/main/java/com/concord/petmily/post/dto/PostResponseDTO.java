package com.concord.petmily.post.dto;

import com.concord.petmily.post.entity.PostCategory;
import com.concord.petmily.post.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시물 데이터 전송 객체
 * 클라이언트와 서버 간의 데이터 전송에 사용
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponseDTO {

    private Long id;
    private Long userId;
    private PostCategory postCategory;
    private String thumbnailPath;
    private String title;
    private String content;
    private String imagePath;
    private Integer viewCount;
    private Integer likeCount;
    private PostStatus postStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String attachmentPaths;

    // 해시태그, 댓글 추가

}
