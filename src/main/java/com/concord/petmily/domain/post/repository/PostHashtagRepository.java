package com.concord.petmily.domain.post.repository;

import com.concord.petmily.domain.post.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
    Set<PostHashtag> findAllByPostId(Long postId);
    Set<PostHashtag> findAllByHashtagId(Long hashtagId);
}