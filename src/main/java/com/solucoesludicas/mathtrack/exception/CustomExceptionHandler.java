package com.solucoesludicas.mathtrack.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorExceptionResponse> handleBadRequestException(BadRequestException ex) {
        var errorResponse = ErrorExceptionResponse.builder()
                .title(ex.getTitle())
                .detail(ex.getDetail())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorExceptionResponse> handleBadRequestException(NotFoundException ex) {
        var errorResponse = ErrorExceptionResponse.builder()
                .title(ex.getTitle())
                .detail(ex.getDetail())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
