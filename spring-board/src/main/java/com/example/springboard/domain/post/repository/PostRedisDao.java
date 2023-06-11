package com.example.springboard.domain.post.repository;

import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.domain.post.service.PostService;
import com.example.springboard.utils.PostKeyUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRedisDao {

    private final RedisTemplate<String, PostReturn> redisTemplate;
    private final PostKeyUtility postKeyUtility;
    private final PostService postService;

    public PostReturn getValue(Long postId) {
        String key = getGenerateKey(postId);

        PostReturn value = redisTemplate.opsForValue().get(key);
        if(value == null) {
            value = postService.getPost(postId);
        }

        setValue(value);

        return value;
    }

    public void increaseView(PostReturn postReturn) {
        postReturn.increaseViews();
        setValue(postReturn);
    }

    public void setValue(PostReturn postReturn) {
        String key = getGenerateKey(postReturn.getId());
        redisTemplate.opsForValue().set(key, postReturn, Duration.ofMinutes(20));
    }

    private String getGenerateKey(Long postId) {
        return postKeyUtility.generateKey(postId);
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void editPost() {
        log.info("[SCHEDULED] UPDATE REDIS - POST INFORMATION");
        Set<String> keys = redisTemplate.keys("post:*");
        for (String key : keys) {
            PostReturn postReturn = redisTemplate.opsForValue().get(key);
            postService.updatePost(postReturn);
        }
    }
}
