package com.example.springboard.domain.post.repository;

import com.example.springboard.utils.PostKeyUtility;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Repository
public class PostRedisDao extends KeyExpirationEventMessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostKeyUtility postKeyUtility;
    private final PostRepository postRepository;

    public PostRedisDao(RedisMessageListenerContainer listenerContainer, RedisTemplate<String, Object> redisTemplate, PostKeyUtility postKeyUtility, PostRepository postRepository) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
        this.postKeyUtility = postKeyUtility;
        this.postRepository = postRepository;
    }


    public int increaseView(Long postId) {
        String key = generateKey(postId, KeyType.REAL);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        if(valueOperations.get(key) == null) {
            valueOperations.set(generateKey(postId, KeyType.REAL), postRepository.getPostById(postId).getViews());
        }
        resetShadowKey(postId, valueOperations);

        return valueOperations.increment(key).intValue();
    }

    private void resetShadowKey(Long postId, ValueOperations<String, Object> valueOperations) {
        valueOperations.set(generateKey(postId, KeyType.SHADOW) , "", Duration.ofMinutes(5));
    }

    public int getViews(Long postId) {
        String key = generateKey(postId, KeyType.REAL);
        return (int) redisTemplate.opsForValue().get(key);
    }

    public int deleteAndGet(Long postId) {
        String key = generateKey(postId, KeyType.REAL);
        return (int) redisTemplate.opsForValue().getAndDelete(key);
    }

    private String generateKey(Long postId, KeyType keyType) {
        return postKeyUtility.generateKey(postId) + ":" + keyType.getMessage();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("EXPIRED KEY [{}]", message.toString());
        long postId = getPostIdFromKey(message);
        postRepository.updateViews(postId, deleteAndGet(postId));
    }

    private static long getPostIdFromKey(Message message) {
        String postIdStr = (String)Arrays.stream(message.toString().split(":")).toArray()[1];
        long postId = Long.parseLong(postIdStr);
        return postId;
    }

    @Getter
    enum KeyType {
        REAL("REAL"),
        SHADOW("SHAD");

        String message;

        KeyType(String r) {
            this.message = r;
        }
    }
}
