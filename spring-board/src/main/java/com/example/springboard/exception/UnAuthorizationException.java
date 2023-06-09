package com.example.springboard.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizationException extends BaseRuntimeException{
    public UnAuthorizationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
