package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ResultadosMetricasCalculadasDTO {
    private ClassificacaoTauUEnum classificacaoTauUAcertos;
    private ClassificacaoTauUEnum classificacaoTauUErros;
    private HabilidadeEnum habilidadeTrabalhada;
    private double tauUAcerto;
    private double tauUErro;
    private double mediaDeTempo;
    private double mediaDeAcerto;
    private double mediaDeErros;
    private long jogoID;
    private int dificuldade;
    private UUID criancaUUID;
    private UUID especialistaUUID;
}
