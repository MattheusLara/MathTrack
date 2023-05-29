package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GerarRelatorioService {
    LocalDateTime gerarRelatorioGeral(final UUID criancaUuid, final PlataformaEnum plataforma) throws Exception;

    String gerarRelatorioHabilidade(final UUID criancaUui, final HabilidadeEnum habilidadeTrabalhada);
}
