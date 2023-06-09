package com.example.springboard.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUtility {

    private static String sessionKey = "MEMBER-SESSION-KEY";

    public Long getSessionValue(HttpSession session) {
        Long memberId = (Long) session.getAttribute(sessionKey);
        return memberId;
    }

    public void setSessionValue(HttpSession session, Long memberId) {
        session.setAttribute(sessionKey, memberId);
    }

    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}
