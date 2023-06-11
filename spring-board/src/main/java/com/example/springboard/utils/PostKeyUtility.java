package com.example.springboard.utils;

import org.springframework.stereotype.Component;

@Component
public class PostKeyUtility {
    private static String prefix = "post";

    public String generateKey(Long postId) {
        return String.join(":", prefix, postId.toString());
    }
}
