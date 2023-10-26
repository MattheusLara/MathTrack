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
    private String especialistaId;
    private long jogoID;
    private int numeroFase;
    private int dificuldadeFase;
    private int numeroAcertos;
    private int numeroErros;
    private int tempoSessao;
    private HabilidadeEnum habilidadeTrabalhada;
    private PlataformaEnum plataforma;
    private CondicoesAdequadasEnum condicoesAdequadas;
}
