package com.solucoesludicas.mathtrack.dto;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class DadosDaMetricaDTO {
    private long jogoID;
    private int numeroDaFase;
    private int dificuldadeFase;
    private UUID criancaUUID;
    private UUID especialistaUUID;
    private HabilidadeEnum habilidadeTrabalhada;
}
