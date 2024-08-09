package com.concord.petmily.post.entity;

import jakarta.persistence.*;

/**
 * 게시물 관련 컨트롤러
 *
 * - 게시물 등록
 * - 게시물 조회
 * - 게시물 수정
 * - 게시물 삭제
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
