package com.solucoesludicas.mathtrack.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseException extends Exception {

    private String detail;
    private String title;

    public BaseException(String title, String detail) {
        super();
        this.detail = detail;
        this.title = title;
    }
}
