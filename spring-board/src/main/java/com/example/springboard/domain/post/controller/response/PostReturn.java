package com.example.springboard.domain.post.controller.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostReturn {
    private Long id;
    private String title;
    private String content;
    private int views;
    private Float score;
    private String nickname;
    private LocalDateTime registerDate;
}
