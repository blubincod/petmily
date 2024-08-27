package com.concord.petmily.domain.comment.entity;

import com.concord.petmily.domain.post.entity.BaseTimeEntity;
import com.concord.petmily.domain.post.entity.Post;
import com.concord.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // 게시물 아이디
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 회원 아이디
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글 내용
    @Column(nullable = false, length = 1000)
    private String content;

    // 부모댓글
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;

}
