package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
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
@Table(name = "metricas_jogo")
public class MetricasJogoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crianca_uuid", nullable = false)
    private UUID criancaUUID;

    @Column(name = "especialista_uuid", nullable = false)
    private UUID especialistaUUID;

    @Column(name = "jogo_id", nullable = false)
    private long jogoID;

    @Column(name = "numero_da_fase", nullable = false)
    private int numeroDaFase;

    @Column(name = "dificuldade_da_fase", nullable = false)
    private int dificuldadeDaFase;

    @Column(name = "numero_de_acertos", nullable = false)
    private int numeroDeAcertos;

    @Column(name = "numero_de_erros", nullable = false)
    private int numeroDeErros;

    @Column(name = "tempo_sessao")
    private int tempoSessao;

    @Column(name = "data_sessao")
    private LocalDateTime dataSessao;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicoes_adequadas", nullable = false)
    private CondicoesAdequadasEnum condicoesAdequadas;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilidade_trabalhada", nullable = false)
    private HabilidadeEnum habilidadeTrabalhada;

    @Enumerated(EnumType.STRING)
    @Column(name = "plataforma", nullable = false)
    private PlataformaEnum plataforma;
}
