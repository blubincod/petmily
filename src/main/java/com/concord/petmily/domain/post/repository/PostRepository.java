package com.concord.petmily.domain.post.repository;

import com.concord.petmily.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시물 저장소 인터페이스
 * 데이터베이스와의 CRUD 작업을 처리
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPostCategoryId(Long categoryId, Pageable pageable);
}
