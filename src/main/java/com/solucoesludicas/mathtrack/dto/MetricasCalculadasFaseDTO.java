package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
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
    private int dificuldade;
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
    private HabilidadeEnum habilidadeTrabalhada;
    private PlataformaEnum plataforma;
    private LocalDateTime dataCalculoMetricas;
}
