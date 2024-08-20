package com.concord.petmily.domain.walk.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * 복합키 클래스
 * - WalkGroup 엔티티를 Pet과 Walk의 식별자를 사용하여 복합 키로 설정.
 * - PA에서 복합 키를 사용하여 엔티티 간의 식별 관계를 설정할 때 사용.
 * - 두 엔티티의 식별자를 조합하여 기본 키로 사용하는 경우에 필요.
 */
@Embeddable
public class WalkGroupId implements Serializable {
    private Long petId;
    private Long walkId;
}
