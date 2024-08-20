package com.concord.petmily.post.repository;

import com.concord.petmily.post.entity.PostView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostViewRepository extends JpaRepository<PostView, Long> {
    Optional<PostView> findByUserIdAndPostId(Long userId, Long postId);
}
