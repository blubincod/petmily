package com.concord.petmily.domain.post.dto;

import com.concord.petmily.domain.post.entity.Post;
import com.concord.petmily.domain.post.entity.PostStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 게시물 데이터 전송 객체
 * 클라이언트와 서버 간의 데이터 전송에 사용
 */
@Getter
@Setter
public class PostDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long categoryId;
        private String title;
        private String content;
        private Set<String> hashtagNames;
        private PostStatus postStatus;

        public Post toEntity() {
            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .postStatus(postStatus)
                    .build();

            return post;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long userId;
        private String categoryName;
        private String thumbnailPath;
        private String title;
        private String content;
        private List<String> imagePaths;
        private Set<String> hashtagNames;
        private Integer viewCount;
        private Integer likeCount;
        private PostStatus postStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response(Post post) {
            this.id = post.getId();
            this.userId = post.getUser().getId();
            this.categoryName = post.getPostCategory().getCategoryName();
            this.thumbnailPath = post.getThumbnailPath();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.imagePaths = post.getImagePaths();
            this.viewCount = post.getViewCount();
            this.likeCount = post.getLikeCount();
            this.postStatus = post.getPostStatus();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseGetPosts {
        private Long id;
        private Long userId;
        private String categoryName;
        private String thumbnailPath;
        private String title;
        private PostStatus postStatus;

        public ResponseGetPosts(Post post) {
            this.id = post.getId();
            this.userId = post.getUser().getId();
            this.categoryName = post.getPostCategory().getCategoryName();
            this.thumbnailPath = post.getThumbnailPath();
            this.title = post.getTitle();
            this.postStatus = post.getPostStatus();
        }
    }
}