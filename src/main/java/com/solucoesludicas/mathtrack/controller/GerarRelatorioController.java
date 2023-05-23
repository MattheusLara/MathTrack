package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.service.GerarRelatorioJogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class GerarRelatorioController {
    private final GerarRelatorioJogoService gerarRelatorioJogoService;

    @PostMapping("/gerar-relatorio-jogo")
    public ResponseEntity<String> gerarRelatorioJogo(@RequestHeader UUID criancaUuid, @RequestHeader Long idJogo) throws Exception{
        if(idJogo == null){
            throw new Exception("Id do jogo é nulo");
        }

        return ResponseEntity.ok(gerarRelatorioJogoService.execute(criancaUuid, idJogo));
    }
}
