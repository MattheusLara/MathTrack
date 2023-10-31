package com.solucoesludicas.mathtrack.service;

import com.solucoesludicas.mathtrack.exception.NotFoundException;
import com.solucoesludicas.mathtrack.models.MetricasCompletasModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;

import java.util.List;

public interface MetricaService {
    void execute(final MetricasJogoModel metricasJogoModel) throws NotFoundException;
    List<MetricasCompletasModel> getAllMetricasByIdentifier(String login) throws NotFoundException;
    void sendSms(String telefone);
}
