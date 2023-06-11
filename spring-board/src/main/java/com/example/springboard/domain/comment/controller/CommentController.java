package com.example.springboard.domain.comment.controller;

import com.example.springboard.aop.annotations.SessionCheck;
import com.example.springboard.domain.comment.controller.response.CommentReturn;
import com.example.springboard.domain.comment.service.CommentService;
import com.example.springboard.domain.post.repository.PostRedisDao;
import com.example.springboard.exception.InvalidRequestException;
import com.example.springboard.utils.SessionUtility;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SessionUtility sessionUtility;

    @GetMapping("/{commentId}")
    public CommentReturn getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping("/{postId}/all")
    public List<CommentReturn> getComments(@PathVariable Long postId,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer count) {
        return commentService.getComments(postId, page, count);
    }

    @SessionCheck
    @PostMapping("/{postId}")
    public void addComment(@RequestBody CommentForm commentForm, @PathVariable Long postId, HttpSession session) {
        Long memberId = getSessionValue(session);
        commentService.addComment(postId, memberId, commentForm.getContent(), commentForm.getScore());
    }

    @SessionCheck
    @PostMapping("/{commentId}/edit")
    public void editComment(@RequestBody CommentForm commentForm, @PathVariable Long commentId, HttpSession session) {
        Long memberId = getSessionValue(session);
        commentForm.checkValid();
        commentService.editComment(memberId, commentId, commentForm.getContent(), commentForm.getScore());
    }

    @SessionCheck
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId, HttpSession session) {
        Long memberId = getSessionValue(session);
        commentService.deleteComment(memberId, commentId);
    }

    private Long getSessionValue(HttpSession session) {
        return sessionUtility.getSessionValue(session);
    }

    @Getter
    static class CommentForm {

        private String content;
        private Float score;

        protected void checkValid() {
            if(!StringUtils.hasText(content) || score == null) {
                throw new InvalidRequestException("내용이나 평점은 비어있을 수 업습니다.", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
