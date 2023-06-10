package com.example.springboard.domain.comment.dto;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class Comment {
    private Long id;
    private String content;
    private float score;
    private Status status;
    private LocalDateTime registerDate;
    private Long memberId;
    private Long postId;

    Comment(Long memberId, Long postId, String content, float score) {
        this.content = content;
        this.score = score;
        this.status = Status.USING;
        this.registerDate = LocalDateTime.now();
        this.memberId = memberId;
        this.postId = postId;
    }

    public static Comment createComment(Long memberId, Long postId, String content, Float score) {
        if(!StringUtils.hasText(content) || score == null) {
            throw new InvalidRequestException("내용이나 평점을 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        return new Comment(memberId, postId, content, score);
    }
}
