package com.concord.petmily.domain.post.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    // 회원번호
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 카테고리 아이디
    @ManyToOne
    @JoinColumn(name = "category_id")
    private PostCategory postCategory;

    // 썸네일 경로
    @Column(name = "thumbnail_path", length = 255)
    private String thumbnailPath;

    // 제목
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    // 내용
    @Column(name = "content", length = 1000)
    private String content;

    // 게시물 사진 경로
    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> imagePaths = new ArrayList<>();

    // 해시태그
    @OneToMany(mappedBy = "post")
    private Set<PostHashtag> hashtags = new HashSet<>();

    // 조회수
    @Column(name = "view_count")
    private int viewCount;

    // 좋아요 수
    @Column(name = "like_count")
    private int likeCount;

    // 상태 (공개, 삭제)
    @Column(name = "status")
    private PostStatus postStatus;

    public void viewCountUp(Post post) {
        post.viewCount++;
    }

    public void likeCountUp(Post post) {
        post.likeCount++;
    }

    public void likeCountDown(Post post) {
        post.likeCount--;
    }
}
