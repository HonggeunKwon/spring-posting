package com.example.springboard.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BaseRuntimeException{
    public InvalidRequestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
