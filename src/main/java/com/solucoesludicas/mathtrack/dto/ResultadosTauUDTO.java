package com.solucoesludicas.mathtrack.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultadosTauUDTO {
    private double TauUAcerto;
    private double TauUErro;
    private double TauUTempo;
}
