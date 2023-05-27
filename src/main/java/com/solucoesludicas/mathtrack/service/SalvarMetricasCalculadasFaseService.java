package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.models.MetricasCalculadasModel;

public interface SalvarMetricasCalculadasFaseService {
    MetricasCalculadasModel execute(final MetricasCalculadasFaseDTO metricasCalculadasFaseModel);
}
