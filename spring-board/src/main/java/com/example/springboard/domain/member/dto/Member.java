package com.example.springboard.domain.member.dto;

import com.example.springboard.domain.common.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String nickname;
    private String loginId;
    private String password;
    private LocalDateTime registerDate;
    private Status status;

    protected Member() { }

    Member(String nickname, String loginId, String password) {
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.registerDate = LocalDateTime.now();
        status = Status.USING;
    }

    public static Member createMember(String nickname, String loginId, String password) {
        return new Member(nickname, loginId, password);
    }
}
