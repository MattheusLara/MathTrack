package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;

import java.util.UUID;

public interface GerarRelatorioJogoService {
    String execute(final UUID criancaUuid, Long idJogo);
}
