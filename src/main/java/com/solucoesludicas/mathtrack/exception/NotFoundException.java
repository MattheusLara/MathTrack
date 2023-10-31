package com.solucoesludicas.mathtrack.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotFoundException extends BaseException {

    public NotFoundException(String title, String detail) {
       super(title, detail);
    }
}
