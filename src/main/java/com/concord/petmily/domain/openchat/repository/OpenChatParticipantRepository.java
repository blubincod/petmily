package com.concord.petmily.domain.openchat.repository;

import com.concord.petmily.domain.openchat.entity.OpenChat;
import com.concord.petmily.domain.openchat.entity.OpenChatParticipant;
import com.concord.petmily.domain.openchat.entity.OpenChatParticipantId;
import com.concord.petmily.domain.openchat.entity.ParticipantStatus;
import com.concord.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenChatParticipantRepository extends JpaRepository<OpenChatParticipant, OpenChatParticipantId> {
    int countByOpenChatAndStatus(OpenChat openChat, ParticipantStatus status);

    Optional<OpenChatParticipant> findByUserIdAndOpenChatId(Long openChatId, Long userId);
}
