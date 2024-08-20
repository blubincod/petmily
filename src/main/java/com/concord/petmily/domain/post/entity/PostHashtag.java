package com.concord.petmily.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_hashtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_hashtag_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    public PostHashtag(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }

}