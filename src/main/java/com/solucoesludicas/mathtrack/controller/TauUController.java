package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
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
    public ResponseEntity<ResultadosTauUDTO> obeterTauU(@RequestHeader UUID criancaUuid){
        return ResponseEntity.ok(calcularTauUService.execute(criancaUuid));
    }
}
