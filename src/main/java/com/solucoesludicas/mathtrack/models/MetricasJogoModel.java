package com.solucoesludicas.mathtrack.models;

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
    private int taxaDeAcertos;
    @Column(nullable = false)
    private int taxaDeErros; //numero ou %?
    @Column
    private int tempoConclusao;
    @Column(nullable = false)
    private LocalDateTime dataSessao;
    @Column
    private boolean condicoesAdequadas; //ambiente do teste estressor, elementos distratores, passou mal, estresse previo, outros, cond adequadas.
}
