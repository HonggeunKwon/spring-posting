package com.example.springboard.domain.post.dto;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class Post {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private Long memberId;
    private Status status;
    private int views;

    protected Post() { }

    Post(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.status = Status.USING;
        this.registerDate = LocalDateTime.now();
        this.memberId = memberId;
    }

    public static Post createPost(String title, String content, Long memberId) {
        if(!StringUtils.hasText(title) || !StringUtils.hasText(content)) {
            throw new InvalidRequestException("제목 혹은 내용은 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        return new Post(title, content, memberId);
    }
}
