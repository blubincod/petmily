package com.concord.petmily.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hashtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name", nullable = false)
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private Set<PostHashtag> posts = new HashSet<>();

    public Hashtag(String hashtagName) {
        this.hashtagName = hashtagName;
    }

}