package com.example.springboard.mapper;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.domain.member.dto.Member;

public interface MemberMapper {
    Member findById(Long memberId);
    int countByLoginIdAndNickname(String loginId, String nickname);
    Member findByLoginIdAndPassword(String loginId, String password);
    int insert(Member member);
    int changeStatus(Long memberId, Status status);
}
