package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
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
    private int numeroDeAcertos;
    private int numeroDeErros;
    private int tempoSessao;
    private HabilidadeEnum habilidadeTrabalhada;
    private PlataformaEnum plataforma;
    private CondicoesAdequadasEnum condicoesAdequadas;
}
