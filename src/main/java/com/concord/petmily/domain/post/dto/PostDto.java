package com.concord.petmily.post.dto;

import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 게시물 데이터 전송 객체
 * 클라이언트와 서버 간의 데이터 전송에 사용
 */
public class PostDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long userId;
        private Long categoryId;
        private String thumbnailPath;
        private String title;
        private String content;
        private String imagePath;
        private Set<String> hashtagNames;
        private PostStatus postStatus;

        public Post toEntity() {
            Post post = Post.builder()
                    .userId(userId)
                    .thumbnailPath(thumbnailPath)
                    .title(title)
                    .content(content)
                    .imagePath(imagePath)
                    .postStatus(postStatus)
                    .build();

            return post;
        }
    }

    @Data
    public static class Response {
        private Long id;
        private Long userId;
        private String categoryName;
        private String thumbnailPath;
        private String title;
        private String content;
        private String imagePath;
        private Set<String> hashtagNames;
        private Integer viewCount;
        private Integer likeCount;
        private PostStatus postStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response(Post post) {
            this.id = post.getId();
            this.userId = post.getUserId();
            this.categoryName = post.getPostCategory().getCategoryName();
            this.thumbnailPath = post.getThumbnailPath();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.imagePath = post.getImagePath();
            this.viewCount = post.getViewCount();
            this.likeCount = post.getLikeCount();
            this.postStatus = post.getPostStatus();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }

    @Data
    public static class ResponseGetPosts {
        private Long id;
        private Long userId;
        private String categoryName;
        private String thumbnailPath;
        private String title;
        private PostStatus postStatus;

        public ResponseGetPosts(Post post) {
            this.id = post.getId();
            this.userId = post.getUserId();
            this.categoryName = post.getPostCategory().getCategoryName();
            this.thumbnailPath = post.getThumbnailPath();
            this.title = post.getTitle();
            this.postStatus = post.getPostStatus();
        }
    }
}