package com.example.springboard.domain.post.repository;

import com.example.springboard.domain.post.controller.response.PostBriefReturn;
import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.domain.post.dto.Post;
import com.example.springboard.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final PostMapper postMapper;

    public PostReturn getPostById(Long id) {
        return postMapper.findById(id);
    }

    public List<PostBriefReturn> findPosts(Integer offset, Integer count) {
        return postMapper.findPosts(offset, count);
    }

    public boolean checkAuth(Long memberId, Long postId) {
        return postMapper.findByMemberIdAndPostId(memberId, postId) == 1;
    }

    public boolean insertPost(Post post) {
        return postMapper.insert(post) == 1;
    }

    public boolean updatePost(Long postId, String title, String content) {
        return postMapper.update(postId, title, content) == 1;
    }

    public boolean deletePost(Long postId) {
        return postMapper.delete(postId) == 1;
    }
}
