package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.SalvarMetricaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalvarMetricaServiceImpl implements SalvarMetricaService {

    private final MetricasJogoRepository metricasJogoRepository;

    @Override
    @Transactional
    public MetricasJogoModel execute(MetricasJogoModel metricasJogoModel) {
        return metricasJogoRepository.save(metricasJogoModel);
    }
}
