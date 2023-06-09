package com.example.springboard.domain.member.controller;

import com.example.springboard.domain.member.service.MemberService;
import com.example.springboard.utils.SessionUtility;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SessionUtility sessionUtility;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpSession session) {
        Long memberId = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
        sessionUtility.setSessionValue(session, memberId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignForm signForm, HttpSession session) {
        Long memberId = memberService.signUp(signForm.getLoginId(), signForm.getPassword(), signForm.getNickname());
        sessionUtility.setSessionValue(session, memberId);
    }

    @DeleteMapping("/sign-out")
    public void signOut(@RequestBody String password, HttpSession session) {
        Long memberId = sessionUtility.getSessionValue(session);
        memberService.signOut(memberId, password);
        sessionUtility.invalidateSession(session);
    }

    @DeleteMapping("/logout")
    public void logout(HttpSession session) {
        sessionUtility.invalidateSession(session);
    }

    @Getter
    static class SignForm {
        private String nickname;
        private String loginId;
        private String password;
    }

    @Getter
    static class LoginForm {
        private String loginId;
        private String password;
    }
}
