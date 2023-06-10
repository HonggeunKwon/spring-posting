package com.example.springboard.mapper;

import com.example.springboard.domain.comment.controller.response.CommentReturn;
import com.example.springboard.domain.comment.dto.Comment;

import java.util.List;

public interface CommentMapper {
    List<CommentReturn> getCommentsByPostId(Long postId, Integer offset, Integer count);
    CommentReturn getCommentById(Long commentId);
    int insert(Comment comment);
    int update(Long commentId, String content, Float score);
    int getCount(Long memberId, Long commentId);
    int delete(Long commentId);
}
