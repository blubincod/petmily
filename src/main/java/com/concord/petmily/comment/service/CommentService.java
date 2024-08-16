package com.concord.petmily.comment.service;

import com.concord.petmily.comment.dto.CommentDto;
import com.concord.petmily.comment.entity.Comment;
import com.concord.petmily.comment.repository.CommentRepository;
import com.concord.petmily.post.entity.Post;
import com.concord.petmily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment insert(Long postId, CommentDto.Request dto) {
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        Comment comment = dto.toEntity();

        Comment parentComment;
        if (dto.getParentId() != null) {
            parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("해당 댓글을 찾을 수 없습니다."));
            comment.setParent(parentComment);
        }

//        comment.setUserId(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 없습니다."));

        if(comment.getIsDeleted()){
            throw new RuntimeException("이미 삭제된 댓글입니다.");
        }
        comment.setIsDeleted(true);
        comment.setContent("댓글이 삭제되었습니다.");
    }

    @Transactional
    public void update(Long commentId, CommentDto.Request dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 없습니다."));

        if(comment.getUserId() != dto.getUserId()) {
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }

        comment.setContent(dto.getContent());
    }
}
