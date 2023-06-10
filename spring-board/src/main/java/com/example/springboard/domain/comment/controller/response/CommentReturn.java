package com.example.springboard.domain.comment.controller.response;

import java.time.LocalDateTime;

public class CommentReturn {
    private Long id;
    private String content;
    private String nickname;
    private float score;
    private LocalDateTime registerDate;

    protected CommentReturn() { }
}
