package com.solucoesludicas.mathtrack.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HabilidadeEnum {
    CLASSIFICAR_OBJETOS("Classificar Objetos"),
    IDENTIFICAR_RELACOES_ESPACIAIS("Identificar Relações Espaciais"),
    IDENTIFICAR_NOCOES_DE_GRANDEZA("Identificar Noções de Grandeza");

    private String value;
}
