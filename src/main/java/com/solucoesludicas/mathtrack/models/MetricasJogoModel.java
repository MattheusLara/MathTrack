package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
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
    private Long id;
    @Column(nullable = false)
    private UUID criancaUUID;
    @Column(nullable = false)
    private UUID especialistaUUID;
    @Column(nullable = false)
    private long jogoID;
    @Column(nullable = false)
    private int numeroDaFase;
    @Column(nullable = false)
    private int dificuldadeDaFase;
    @Column(nullable = false)
    private int numeroDeAcertos;
    @Column(nullable = false)
    private int numeroDeErros; //numero ou %?
    @Column
    private int tempoSessao;
    @Column
    private LocalDateTime dataSessao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CondicoesAdequadasEnum condicoesAdequadas;
}
