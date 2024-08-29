package com.concord.petmily.domain.likes.repository;

import com.concord.petmily.domain.likes.entity.Likes;
import com.concord.petmily.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserIdAndPost(Long UserId, Post post);

}
