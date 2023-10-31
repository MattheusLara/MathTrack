package com.solucoesludicas.mathtrack.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorExceptionResponse {
    private String title;
    private String detail;

    public ErrorExceptionResponse(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

}
