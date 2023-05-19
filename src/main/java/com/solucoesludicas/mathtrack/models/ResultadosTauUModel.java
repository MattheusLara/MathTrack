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
public class ResultadosTauUModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private UUID criancaUUID;
    @Column(nullable = false)
    private UUID especialistaUUID;
    @Column(nullable = false)
    private double tauUAcertos;
    @Column(nullable = false)
    private double tauUErros;
    @Column(nullable = false)
    private double tauUTempo;
    @Column(nullable = false)
    private boolean condicoesAdequadas;
    @Column(nullable = false)
    private LocalDateTime dataCalculo;
}
