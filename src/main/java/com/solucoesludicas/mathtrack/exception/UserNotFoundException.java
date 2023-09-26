package com.solucoesludicas.mathtrack.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, LogLevel.ERROR, "details", "title");
    }
}
