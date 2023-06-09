package com.example.springboard.domain.post.service;

import com.example.springboard.domain.post.controller.response.PostBriefReturn;
import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.domain.post.dto.Post;
import com.example.springboard.domain.post.repository.PostRepository;
import com.example.springboard.exception.InvalidRequestException;
import com.example.springboard.exception.NoResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostReturn getPost(Long postId) {
        PostReturn postReturn = postRepository.getPostById(postId);
        if(postReturn == null) {
            throw new NoResourceException("게시물이 삭제되었거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return postReturn;
    }

    public List<PostBriefReturn> getPosts(Integer page, Integer count) {
        List<PostBriefReturn> posts = postRepository.findPosts(page - 1, count);
        return posts;
    }

    public void addPost(String title, String content, Long memberId) {
        Post post = Post.createPost(title, content, memberId);
        boolean result = postRepository.insertPost(post);
        if(!result) {
            throw new RuntimeException("[POST-SERVICE] 삽입 중 오류 발생");
        }
    }

    public void editPost(Long postId, Long memberId, String title, String content) {
        checkValid(title, content);
        checkAuth(postId, memberId);
        postRepository.updatePost(postId, title, content);
    }

    public void deletePost(Long postId, Long memberId) {
        checkAuth(postId, memberId);
        postRepository.deletePost(postId);
    }

    private void checkAuth(Long postId, Long memberId) {
        postRepository.checkAuth(memberId, postId);
    }

    private void checkValid(String title, String content) {
        if(!StringUtils.hasText(title) && !StringUtils.hasText(content)) {
            throw new InvalidRequestException("제목 혹은 내용은 비어있을 수 없습니다", HttpStatus.BAD_REQUEST);
        }
    }
}
