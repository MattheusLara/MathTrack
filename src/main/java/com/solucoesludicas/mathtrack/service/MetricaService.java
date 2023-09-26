package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.models.MetricasJogoModel;

import java.util.List;
import java.util.UUID;

public interface MetricaService {
    void execute(final MetricasJogoModel metricasJogoModel);
    List<MetricasJogoModel> getAllMetricasByEspecialista(UUID especialista);
    void sendSms(String telefone);
}
