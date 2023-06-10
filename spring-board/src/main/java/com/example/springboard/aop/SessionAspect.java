package com.example.springboard.aop;

import com.example.springboard.exception.UnAuthorizationException;
import com.example.springboard.utils.SessionUtility;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class SessionAspect {

    private final SessionUtility sessionUtility;

    @Before("@annotation(com.example.springboard.aop.annotations.SessionCheck)")
    public void sessionCheck(JoinPoint joinPoint) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest()
                .getSession();

        Long memberId = sessionUtility.getSessionValue(session);
        if(memberId == null) {
            throw new UnAuthorizationException("로그인 후에 진행해주세요.", HttpStatus.FORBIDDEN);
        }
    }
}
