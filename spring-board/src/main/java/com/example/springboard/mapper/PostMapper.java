package com.example.springboard.mapper;

import com.example.springboard.domain.post.controller.response.PostBriefReturn;
import com.example.springboard.domain.post.controller.response.PostReturn;
import com.example.springboard.domain.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PostMapper {
    PostReturn findById(@Param("id") Long id);
    List<PostBriefReturn> findPosts(@Param("offset") Integer offset, @Param("count") Integer count);
    int findByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
    int insert(Post post);
    int update(@Param("postId") Long postId, @Param("title") String title, @Param("content") String content);
    int delete(Long postId);
    void updateViews(Long postId, int views);
}
