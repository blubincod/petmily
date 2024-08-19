package com.concord.petmily.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시물 카테고리
 *
 * id - categoryName
 * 1 - 공지사항
 * 2 - 가입 인사
 * 3 - 정보 공유
 * 4 - 산책 인증
 * 5 - 자유 게시판
 * 6 - 리뷰 게시판
 * 7 - QnA
 */
@Entity
@Table(name = "post_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

}
