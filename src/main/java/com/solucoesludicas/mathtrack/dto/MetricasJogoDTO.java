package com.solucoesludicas.mathtrack.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MetricasJogoDTO {
    private UUID criancaUUID;
    private UUID especialistaUUID;
    private long jogoID;
    private int numeroDaFase;
    private int dificuldadeDaFase;
    private int taxaDeAcertos;
    private int taxaDeErros;
    private int tempoSessao;
    private boolean condicoesAdequadas;
}
