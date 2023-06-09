package com.example.springboard.exception;

import org.springframework.http.HttpStatus;

public class NoResourceException extends BaseRuntimeException{
    public NoResourceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
