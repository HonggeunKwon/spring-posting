package com.example.springboard.domain.comment.repository;

import com.example.springboard.domain.comment.controller.response.CommentReturn;
import com.example.springboard.domain.comment.dto.Comment;
import com.example.springboard.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final CommentMapper commentMapper;

    public List<CommentReturn> getCommentsByPostId(Long postId, Integer offset, Integer count) {
        return commentMapper.getCommentsByPostId(postId, offset, count);
    }

    public CommentReturn getCommentById(Long commentId) {
        return commentMapper.getCommentById(commentId);
    }

    public boolean insertComment(Comment comment) {
        return commentMapper.insert(comment) == 1;
    }

    public boolean updateComment(Long commentId, String content, Float score) {
        return commentMapper.update(commentId, content, score) == 1;
    }

    public boolean checkAuthorization(Long memberId, Long commentId) {
        return commentMapper.getCount(memberId, commentId) == 1;
    }

    public boolean deleteComment(Long commentId) {
        return commentMapper.delete(commentId) == 1;
    }
}
