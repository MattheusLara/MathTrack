package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;

import java.util.UUID;

public interface CalcularTauUService {
    ResultadosTauUDTO execute(final UUID resultadosTauUDTO, final boolean somenteCondicoesAdequadas);
}
