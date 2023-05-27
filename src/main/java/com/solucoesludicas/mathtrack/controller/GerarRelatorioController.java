package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.repository.CriancasRepository;
import com.solucoesludicas.mathtrack.service.GerarRelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class GerarRelatorioController {
    private final CriancasRepository criancasRepository;
    private final GerarRelatorioService gerarRelatorioService;

    @PostMapping("/gerar-relatorio-jogo/geral")
    public ResponseEntity<String> gerarRelatorioJogo(@RequestHeader UUID criancaUuid) throws Exception{
        if(!criancasRepository.existsById(criancaUuid)){
            throw new Exception("Nao foi possivel escontrar a crianca com esse uuid");
        }

        return ResponseEntity.ok(gerarRelatorioService.gerarRelatorioGeral(criancaUuid));
    }

    @PostMapping("/gerar-relatorio-jogo/habilidade")
    public ResponseEntity<String> gerarRelatorioJogo(@RequestHeader UUID criancaUuid, @RequestHeader HabilidadeEnum habilidadeTrabalhada) throws Exception{
        if(!criancasRepository.existsById(criancaUuid)){
            throw new Exception("Nao foi possivel escontrar a crianca com esse uuid");
        }

        return ResponseEntity.ok(gerarRelatorioService.gerarRelatorioHabilidade(criancaUuid, habilidadeTrabalhada));
    }
}
