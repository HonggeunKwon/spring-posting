package com.example.springboard.domain.post.controller.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    private int count;
    private Float score;
    private String nickname;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registerDate;


    public void increaseViews() {
        this.views++;
    }

    public void appendComment(Float score) {
        this.score =  (this.score * count + score) / count++;
    }
}
