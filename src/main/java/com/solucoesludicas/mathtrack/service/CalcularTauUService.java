package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;

import java.util.UUID;

public interface CalcularTauUService {
    ResultadosTauUDTO execute(final UUID criancaUuid, final boolean somenteCondicoesAdequadas, final HabilidadeEnum habilidadeTrabalhada, final int dificuldade, final PlataformaEnum plataforma) throws Exception;
}
