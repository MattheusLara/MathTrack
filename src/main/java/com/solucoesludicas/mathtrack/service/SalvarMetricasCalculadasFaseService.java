package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.models.MetricasCalculadasFaseModel;

public interface SalvarMetricasCalculadasFaseService {
    MetricasCalculadasFaseModel execute(final MetricasCalculadasFaseDTO metricasCalculadasFaseModel);
}
