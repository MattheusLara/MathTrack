package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.models.MetricasCompletasModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;

import java.util.List;

public interface MetricaService {
    void execute(final MetricasJogoModel metricasJogoModel);
    List<MetricasCompletasModel> getAllMetricasByIdentifier(String role, String identificador);
    void sendSms(String telefone);
}
