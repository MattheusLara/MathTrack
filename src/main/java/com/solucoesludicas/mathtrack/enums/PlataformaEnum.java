package com.solucoesludicas.mathtrack.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlataformaEnum {
    MOBILE("Mobile"),
    VR("VR");

    private String value;
}
