package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "metricas_calculadas_fase")
public class MetricasCalculadasFaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private UUID criancaUUID;
    @Column(nullable = false)
    private long jogoID;
    @Column(nullable = false)
    private int numeroDaFase;
    @Column(nullable = false)
    private int dificuldadeDaFase;

    @Column(nullable = false)
    private double mediaAcertosCondAdequadas;
    @Column(nullable = false)
    private double mediaErrosCondAdequadas;
    @Column(nullable = false)
    private double mediaTempoCondAdequadas;
    @Column(nullable = false)
    private double tauUAcertosCondAdequadas;
    @Column(nullable = false)
    private double tauUErrosCondAdequadas;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondAdequadas;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUErrosCondAdequadas;

    @Column(nullable = false)
    private double mediaAcertosCondNaoAdequadas;
    @Column(nullable = false)
    private double mediaErrosCondNaoAdequadas;
    @Column(nullable = false)
    private double mediaTempoCondNaoAdequadas;
    @Column(nullable = false)
    private double tauUAcertosCondNaoAdequadas;
    @Column(nullable = false)
    private double tauUErrosCondNaoAdequadas;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondNaoAdequadas;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUErrosCondNaoAdequadas;

    @Column
    private LocalDateTime dataCalculoMetricas;
}
