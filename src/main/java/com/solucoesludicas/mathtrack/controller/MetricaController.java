package com.solucoesludicas.mathtrack.controller;

import com.solucoesludicas.mathtrack.dto.MetricasJogoDTO;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.service.MetricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("metricas")
public class MetricaController {
    private final MetricaService metricaService;

    @PostMapping("/save")
    public ResponseEntity salvarMetrica(@RequestBody MetricasJogoDTO metricasJogoDTO){
        var metricasJogoModel = new MetricasJogoModel();
        BeanUtils.copyProperties(metricasJogoDTO, metricasJogoModel);

        metricasJogoModel.setDataSessao(LocalDateTime.now(ZoneId.of("UTC")));
        metricaService.execute(metricasJogoModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{uuid}")
    public ResponseEntity<List<MetricasJogoModel>> getMetrica(@PathVariable String uuid){
        return ResponseEntity.ok(metricaService.getAllMetricasByEspecialista(UUID.fromString(uuid)));
    }
}
