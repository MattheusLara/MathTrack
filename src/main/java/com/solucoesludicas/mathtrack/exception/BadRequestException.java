package com.solucoesludicas.mathtrack.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BadRequestException extends BaseException {

    public BadRequestException(String title, String detail) {
       super(title, detail);
    }
}
