package com.example.springboard.domain.post.controller.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostBriefReturn {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime registerDate;

    protected PostBriefReturn() { }
}

