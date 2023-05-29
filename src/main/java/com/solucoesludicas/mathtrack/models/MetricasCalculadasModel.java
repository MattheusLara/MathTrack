package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "metricas_calculadas")
public class MetricasCalculadasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crianca_uuid", nullable = false)
    private UUID criancaUUID;

    @Column(name = "dificuldade", nullable = false)
    private int dificuldade;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilidade_trabalhada", nullable = false)
    private HabilidadeEnum habilidadeTrabalhada;

    @Enumerated(EnumType.STRING)
    @Column(name = "plataforma", nullable = false)
    private PlataformaEnum plataforma;

    @Column(name = "media_acertos_cond_adequadas")
    private double mediaAcertosCondAdequadas;

    @Column(name = "media_erros_cond_adequadas")
    private double mediaErrosCondAdequadas;

    @Column(name = "media_tempo_cond_adequadas", nullable = false)
    private double mediaTempoCondAdequadas;

    @Column(name = "tau_u_acertos_cond_adequadas", nullable = false)
    private double tauUAcertosCondAdequadas;

    @Column(name = "tau_u_erros_cond_adequadas", nullable = false)
    private double tauUErrosCondAdequadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_tau_u_acertos_cond_adequadas", nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondAdequadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_tau_u_erros_cond_adequadas", nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUErrosCondAdequadas;

    @Column(name = "media_acertos_cond_nao_adequadas", nullable = false)
    private double mediaAcertosCondNaoAdequadas;

    @Column(name = "media_erros_cond_nao_adequadas", nullable = false)
    private double mediaErrosCondNaoAdequadas;

    @Column(name = "media_tempo_cond_nao_adequadas", nullable = false)
    private double mediaTempoCondNaoAdequadas;

    @Column(name = "tau_u_acertos_cond_nao_adequadas", nullable = false)
    private double tauUAcertosCondNaoAdequadas;

    @Column(name = "tau_u_erros_cond_nao_adequadas", nullable = false)
    private double tauUErrosCondNaoAdequadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_tau_u_acertos_cond_nao_adequadas", nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUAcertosCondNaoAdequadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_tau_u_erros_cond_nao_adequadas", nullable = false)
    private ClassificacaoTauUEnum classificacaoTauUErrosCondNaoAdequadas;

    @Column(name = "data_calculo_metricas")
    private LocalDateTime dataCalculoMetricas;
}
