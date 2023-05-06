package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.MetricasJogoDTO;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.service.SalvarMetricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SalvarMetricaController {
    private final SalvarMetricaService salvarMetricaService;

    @PostMapping("/salvar-metrica-do-jogo")
    public ResponseEntity<Object> salvarMetrica(@RequestBody MetricasJogoDTO metricasJogoDTO){
        var metricasJogoModel = new MetricasJogoModel();
        BeanUtils.copyProperties(metricasJogoDTO, metricasJogoModel);

        metricasJogoModel.setDataSessao(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.ok(salvarMetricaService.execute(metricasJogoModel));
    }

}
