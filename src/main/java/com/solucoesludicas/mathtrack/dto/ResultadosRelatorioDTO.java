package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ResultadosRelatorioDTO {
    private ClassificacaoTauUEnum classificacaoTauUAcertos;
    private ClassificacaoTauUEnum classificacaoTauUErros;
    private double tauUAcerto;
    private double tauUErro;
    private double mediaDeTempo;
    private double mediaDeAcerto;
    private double mediaDeErros;
    private long jogoID;
    private int numeroDaFase;
    private int dificuldadeDaFase;
    private UUID criancaUUID;
    private UUID especialistaUUID;
}
