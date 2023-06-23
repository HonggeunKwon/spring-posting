package com.example.springboard.domain.comment.service;

import com.example.springboard.domain.comment.controller.response.CommentReturn;
import com.example.springboard.domain.comment.dto.Comment;
import com.example.springboard.domain.comment.repository.CommentRepository;
import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.exception.NoResourceException;
import com.example.springboard.exception.UnAuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentReturn getComment(Long commentId) {
        CommentReturn commentReturn = commentRepository.getCommentById(commentId);
        if(commentReturn == null) {
            throw new NoResourceException("댓글이 없거나 삭제되었습니다.", HttpStatus.BAD_REQUEST);
        }

        return commentReturn;
    }

    public List<CommentReturn> getComments(Long postId, Integer page, Integer count) {
        return commentRepository.getCommentsByPostId(postId, page - 1, count);
    }

    public void addComment(Long postId, Long memberId, String content, Float score) {
        Comment comment = Comment.createComment(memberId, postId, content, score);

        boolean result = commentRepository.insertComment(comment);
        if(!result) {
            throw new RuntimeException("삽입에 실패 했습니다.");
        }
    }

    public void editComment(Long memberId, Long commentId, String content, Float score) {
        checkAuthorization(memberId, commentId);
        if (commentRepository.updateComment(memberId, content, score) == false) {
            throw new RuntimeException("수정에 실패 했습니다.");
        }
    }

    public void deleteComment(Long memberId, Long commentId) {
        checkAuthorization(memberId, commentId);
        if(commentRepository.deleteComment(commentId) == false) {
            throw new RuntimeException("삭제에 실패 했습니다.");
        }
    }

    private void checkAuthorization(Long memberId, Long commentId) {
        if (commentRepository.checkAuthorization(memberId, commentId) == false) {
            throw new UnAuthorizationException("권한이 없는 요청입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
