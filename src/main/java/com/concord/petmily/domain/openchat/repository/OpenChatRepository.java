package com.concord.petmily.domain.openchat.repository;

import com.concord.petmily.domain.openchat.entity.OpenChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenChatRepository extends JpaRepository<OpenChat, Long> {
    Page<OpenChat> findAll(Pageable pageable);
}
