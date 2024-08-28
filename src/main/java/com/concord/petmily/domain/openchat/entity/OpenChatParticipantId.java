package com.concord.petmily.domain.openchat.entity;

import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 복합 키 클래스
 * <p>
 * 복합 키 클래스는 Serializable 인터페이스를 구현 필요, 객체의 직렬화를 가능하게 함
 */
@Embeddable // 다른 엔티티에 포함될 수 있는 임베디드 타입
@NoArgsConstructor
@AllArgsConstructor
public class OpenChatParticipantId implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "open_chat_id")
    private OpenChat openChat;

    // OpenChatParticipantId 객체(userId와 openChatId)가 동일한지 비교하는 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenChatParticipantId that = (OpenChatParticipantId) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(openChat, that.openChat);
    }

    // 객체의 해시 코드를 생성하는 메서드
    // HashSet, HashMap 등의 해시 기반 자료구조에서 객체를 빠르게 찾게 함
    @Override
    public int hashCode() {
        return Objects.hash(user, openChat);
    }
}
