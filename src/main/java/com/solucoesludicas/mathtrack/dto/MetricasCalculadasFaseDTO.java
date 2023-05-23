package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class MetricasCalculadasFaseDTO {
    private UUID criancaUUID;
    private long jogoID;
    private int numeroDaFase;
    private int dificuldadeDaFase;
    private double mediaAcertosCondAdequadas;
    private double mediaErrosCondAdequadas;
    private double mediaTempoCondAdequadas;
    private double tauUAcertosCondAdequadas;
    private double tauUErrosCondAdequadas;
    private double mediaAcertosCondNaoAdequadas;
    private double mediaErrosCondNaoAdequadas;
    private double mediaTempoCondNaoAdequadas;
    private double tauUAcertosCondNaoAdequadas;
    private double tauUErrosCondNaoAdequadas;
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondAdequadas;
    private ClassificacaoTauUEnum classificacaoTauUErrosCondAdequadas;
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondNaoAdequadas;
    private ClassificacaoTauUEnum classificacaoTauUErrosCondNaoAdequadas;
    private LocalDateTime dataCalculoMetricas;
}
