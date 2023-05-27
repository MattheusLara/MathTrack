package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;

import java.util.UUID;

public interface GerarRelatorioService {
    String gerarRelatorioGeral(final UUID criancaUuid) throws Exception;

    String gerarRelatorioHabilidade(final UUID criancaUui, final HabilidadeEnum habilidadeTrabalhada);
}
