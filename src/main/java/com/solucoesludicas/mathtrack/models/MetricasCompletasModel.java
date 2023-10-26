package com.solucoesludicas.mathtrack.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MetricasCompletasModel {
    private String criancaNome;
    private String jogoNome;
    private int numeroFase;
    private int dificuldadeFase;
    private int numeroAcertos;
    private int numeroErros;
    private int tempoSessao;
    private LocalDateTime dataSessao;
    private String habilidadeTrabalhada;
    private String plataforma;
}
