package com.example.springboard.domain.member.service;

import com.example.springboard.domain.common.enums.Status;
import com.example.springboard.domain.member.dto.Member;
import com.example.springboard.domain.member.repository.MemberRepository;
import com.example.springboard.exception.BaseRuntimeException;
import com.example.springboard.exception.DuplicationException;
import com.example.springboard.exception.NoResourceException;
import com.example.springboard.exception.UnAuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long login(String loginId, String password) {
        Member member = memberRepository.findMemberByLoginIdAndPassword(loginId, password);
        if(member == null) {
            throw new NoResourceException("아이디 혹은 비밀번호를 정확히 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        return member.getId();
    }

    public Long signUp(String loginId, String password, String nickname) {
        boolean isDuplicate = memberRepository.isExistMemberByLoginIdOrNickname(loginId, nickname);

        if(isDuplicate) {
            throw new DuplicationException("중복된 아이디 혹은 닉네임이 있습니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = insertMember(loginId, password, nickname);
        return member.getId();
    }

    public void signOut(Long memberId, String password) {
        Member member = memberRepository.findMemberById(memberId);
        if(member == null && !member.getPassword().equals(password)) {
            throw new UnAuthorizationException("비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST);
        }
        boolean isSuccess = memberRepository.updateStatusByMemberId(memberId, Status.DELETED);

        if(!isSuccess) {
            throw new RuntimeException("회원 탈퇴에 실패했습니다.");
        }
    }

    private Member insertMember(String loginId, String password, String nickname) {
        Member member = Member.createMember(nickname, loginId, password);
        memberRepository.insertMember(member);
        return member;
    }
}
