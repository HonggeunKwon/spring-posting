package com.example.springboard.domain.member.repository;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.domain.member.dto.Member;
import com.example.springboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final MemberMapper memberMapper;

    public Member findMemberById(Long memberId) {
        return memberMapper.findById(memberId);
    }

    public boolean isExistMemberByLoginIdOrNickname(String loginId, String nickname) {
        return memberMapper.countByLoginIdAndNickname(loginId, nickname) != 0;
    }

    public Member findMemberByLoginIdAndPassword(String loginId, String password) {
        return memberMapper.findByLoginIdAndPassword(loginId, password);
    }

    public boolean insertMember(Member member) {
        return memberMapper.insert(member) == 1;
    }

    public boolean updateStatusByMemberId(Long memberId, Status status) {
        return memberMapper.changeStatus(memberId, status) == 1;
    }
}
