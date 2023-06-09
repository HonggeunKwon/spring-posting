package com.example.springboard.domain.post.controller.response;


import java.time.LocalDateTime;

public class PostReturn {
    private Long id;
    private String title;
    private String content;
    private int views;
    private float score;
    private String nickname;
    private LocalDateTime registerDate;
}
