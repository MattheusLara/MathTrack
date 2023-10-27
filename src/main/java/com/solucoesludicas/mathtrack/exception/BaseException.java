package com.solucoesludicas.mathtrack.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class BaseException extends RuntimeException {

    public static final String PROBLEM_JSON = "application/problem+json";

    private final HttpStatus httpStatus;
    private final LogLevel logLevel;
    private final String details;
    private final String title;

    public BaseException(HttpStatus httpStatus, LogLevel logLevel, String details, String title) {
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
        this.details = details;
        this.title = title;
    }

}
