package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.models.MetricasCalculadasFaseModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasCalculadasFaseRepository;
import com.solucoesludicas.mathtrack.service.SalvarMetricasCalculadasFaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SalvarMetricasCalculadasServiceImpl implements SalvarMetricasCalculadasFaseService {

    private final MetricasCalculadasFaseRepository metricasCalculadasFaseRepository;

    @Transactional
    public MetricasCalculadasFaseModel execute(MetricasCalculadasFaseDTO metricasCalculadasFaseDTO) {
        var metricasCalculadasFaseModel = new MetricasCalculadasFaseModel();
        BeanUtils.copyProperties(metricasCalculadasFaseDTO, metricasCalculadasFaseModel);

        return metricasCalculadasFaseRepository.save(metricasCalculadasFaseModel);
    }
}
