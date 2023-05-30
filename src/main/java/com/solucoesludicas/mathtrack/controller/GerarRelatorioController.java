package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
import com.solucoesludicas.mathtrack.repository.CriancasRepository;
import com.solucoesludicas.mathtrack.service.GerarRelatorioService;
import com.solucoesludicas.mathtrack.service.JasperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class GerarRelatorioController {
    private final CriancasRepository criancasRepository;
    private final GerarRelatorioService gerarRelatorioService;
    private final JasperService jasperService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/gerar-relatorio-jogo/geral")
    public ResponseEntity<byte[]> gerarRelatorioJogo(@RequestHeader UUID criancaUuid, @RequestHeader PlataformaEnum plataforma) throws Exception{

        if (!criancasRepository.existsById(criancaUuid)) {
            throw new Exception("Nao foi possivel escontrar a crianca com esse uuid");
        }

        var dataCalculoDeMetricas = gerarRelatorioService.gerarRelatorioGeral(criancaUuid, plataforma);

        jasperService.addParams("criancaUUID", criancaUuid.toString());
        jasperService.addParams("plataforma", plataforma);
        jasperService.addParams("dataCriacaoRelatorio", dataCalculoDeMetricas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "inline; filename=RelatorioGeral.pdf");

        byte[] pdf = jasperService.exportarPDF("RelatorioGeral");

        if (pdf == null){
            throw new Exception("Erro ao gerar o relatorio");
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }

    @PostMapping("/gerar-relatorio-jogo/habilidade")
    public ResponseEntity<String> gerarRelatorioJogo(@RequestHeader UUID criancaUuid, @RequestHeader HabilidadeEnum habilidadeTrabalhada) throws Exception{
        if(!criancasRepository.existsById(criancaUuid)){
            throw new Exception("Nao foi possivel escontrar a crianca com esse uuid");
        }

        return ResponseEntity.ok(gerarRelatorioService.gerarRelatorioHabilidade(criancaUuid, habilidadeTrabalhada));
    }
}
