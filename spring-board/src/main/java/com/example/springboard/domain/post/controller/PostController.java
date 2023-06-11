package com.example.springboard.domain.post.controller;

import com.example.springboard.aop.annotations.SessionCheck;
import com.example.springboard.domain.post.controller.response.PostBriefReturn;
import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.domain.post.repository.PostRedisDao;
import com.example.springboard.domain.post.service.PostService;
import com.example.springboard.utils.SessionUtility;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final SessionUtility sessionUtility;
    private final PostRedisDao postDao;

    @GetMapping("/{postId}")
    public Object getPost(@PathVariable Long postId) {
        PostReturn value = postDao.getValue(postId);
        postDao.increaseView(value);
        return value;
    }

    @GetMapping("/all")
    public List<PostBriefReturn> getPosts(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer count) {
        return postService.getPosts(page, count);
    }

    @SessionCheck
    @PostMapping
    public ResponseEntity addPost(@RequestBody PostForm postForm, HttpSession session) {
        Long memberId = getMemberId(session);
        postService.addPost(postForm.getTitle(), postForm.getContent(), memberId);

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @PatchMapping("/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostForm postForm, HttpSession session) {
        Long memberId = getMemberId(session);
        postService.editPost(postId, memberId, postForm.getTitle(), postForm.getContent());

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @DeleteMapping("/{postId}")
    public ResponseEntity removePost(@PathVariable Long postId, HttpSession session) {
        Long memberId = getMemberId(session);
        postService.deletePost(postId, memberId);

        return ResponseEntity.ok().build();
    }

    private Long getMemberId(HttpSession session) {
        Long memberId = sessionUtility.getSessionValue(session);
        return memberId;
    }

    @Getter
    static class PostForm {
        private String title;
        private String content;
    }
}
