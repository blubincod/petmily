package com.concord.petmily.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // 회원번호
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private Long userId;

    // 카테고리 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PostCategory postCategory;

    // 썸네일 경로
    @Column(name = "thumbnail_path", length = 50)
    private String thumbnailPath;

    // 제목
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    // 내용
    @Column(name = "content", length = 1000)
    private String content;

    // 게시물 사진 경로
    @Column(name = "content_image_path", length = 100)
    private String imagePath;

    // 조회수
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Integer viewCount;

    // 좋아요 수
    @Column(name = "like_count")
    @ColumnDefault("0")
    private Integer likeCount;

    // 상태 (공개, 삭제)
    @Column(name = "status")
    private PostStatus postStatus;

    // 작성일시
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정일시
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 첨부파일 경로
    @Column(name = "attachment_paths", length = 100)
    private String attachmentPaths;

}
