package com.concord.petmily.comment.dto;

import com.concord.petmily.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long userId;
        private Long parentId;
        private String content;

        public Comment toEntity() {
            return Comment.builder()
                    .userId(userId)
                    .content(content)
                    .build();
        }
    }

    @Data
    public static class Response {
        private Long id;
        private String content;
        private Long userId;
    }
}
