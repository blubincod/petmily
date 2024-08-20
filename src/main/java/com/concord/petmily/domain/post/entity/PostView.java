package com.concord.petmily.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_view")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_view_id")
    private Long id;

    // 회원번호
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "view_timestamp")
    private Long viewTimestamp;

}
