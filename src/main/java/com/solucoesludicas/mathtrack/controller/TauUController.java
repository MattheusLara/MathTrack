package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
import com.solucoesludicas.mathtrack.service.CalcularTauUService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class TauUController {
    private final CalcularTauUService calcularTauUService;

    @GetMapping("/obter-tauU")
    public ResponseEntity<ResultadosTauUDTO> obeterTauU(@RequestHeader UUID criancaUuid, @RequestHeader boolean somenteCondicoesAdequadas, @RequestHeader HabilidadeEnum habilidadeTrabalhada, @RequestHeader int dificuldade, @RequestHeader PlataformaEnum plataforma) throws Exception {
        return ResponseEntity.ok(calcularTauUService.execute(criancaUuid, somenteCondicoesAdequadas, habilidadeTrabalhada, dificuldade, plataforma));
    }
}
