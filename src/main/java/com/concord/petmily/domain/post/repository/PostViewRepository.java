package com.concord.petmily.domain.post.repository;

import com.concord.petmily.domain.post.entity.PostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {
    Optional<PostView> findByUserIdAndPostId(Long userId, Long postId);
}