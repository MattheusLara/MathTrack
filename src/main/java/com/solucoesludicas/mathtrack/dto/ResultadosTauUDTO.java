package com.solucoesludicas.mathtrack.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultadosTauUDTO {
    private double tauUAcerto;
    private double tauUErro;
    private double tauUTempo;
}
