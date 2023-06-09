package com.example.springboard.exception;

import org.springframework.http.HttpStatus;

public class DuplicationException extends BaseRuntimeException{
    public DuplicationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
