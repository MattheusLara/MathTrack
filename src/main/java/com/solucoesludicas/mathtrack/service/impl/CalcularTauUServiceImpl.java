package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.CalcularTauUService;
import com.solucoesludicas.mathtrack.utils.CalculadoraTauU;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalcularTauUServiceImpl implements CalcularTauUService {

    private final MetricasJogoRepository metricasJogoRepository;

    @Override
    @Transactional
    public ResultadosTauUDTO execute(UUID uuid) {
        var metricasJogoCrianca = obterTodasMetricasValidasCrianca(uuid);

        var taxasAcertos = obterListaDeAcertoPelasMetricas(metricasJogoCrianca);
        var taxasErros = obterListaDeErrosPelasMetricas(metricasJogoCrianca);
        var temposSessoes = obterListaDeTempoPelasMetricas(metricasJogoCrianca);

        var tauAcerto = calcularTauUPelaListaCompleta(taxasAcertos);
        var tauErro = calcularTauUPelaListaCompleta(taxasErros);
        var tauTempo = calcularTauUPelaListaCompleta(temposSessoes);

        return ResultadosTauUDTO.builder().TauUAcerto(tauAcerto).TauUErro(tauErro).TauUTempo(tauTempo).build();
    }

    public List<MetricasJogoModel> obterTodasMetricasValidasCrianca(UUID uuid){
        return metricasJogoRepository.searchAllByCriancaUUIDOrderById(uuid);
    }

    private double calcularTauUPelaListaCompleta(List<Double> listaCompleta){
        if (listaCompleta.size() % 2 != 0) {
            listaCompleta = listaCompleta.subList(1, listaCompleta.size());
        }

        var meio = listaCompleta.size() / 2;
        var linhaDeBase = listaCompleta.subList(0, meio);
        var linhaDeIntervencao = listaCompleta.subList(meio, listaCompleta.size());

        try {
            return CalculadoraTauU.calcularTauU(linhaDeBase, linhaDeIntervencao);
        }
        catch (Exception ex){
            return 0;
        }
    }

    private List<Double> obterListaDeAcertoPelasMetricas(List<MetricasJogoModel> metricasJogoDTOList){
        return metricasJogoDTOList.stream()
                .mapToDouble(MetricasJogoModel::getTaxaDeAcertos)
                .boxed()
                .toList();
    }

    private List<Double> obterListaDeErrosPelasMetricas(List<MetricasJogoModel> metricasJogoDTOList){
        return metricasJogoDTOList.stream()
                .mapToDouble(MetricasJogoModel::getTaxaDeErros)
                .boxed()
                .toList();
    }

    private List<Double> obterListaDeTempoPelasMetricas(List<MetricasJogoModel> metricasJogoDTOList){
        return metricasJogoDTOList.stream()
                .mapToDouble(MetricasJogoModel::getTempoSessao)
                .boxed()
                .toList();
    }
}
