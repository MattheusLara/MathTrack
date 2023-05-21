package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
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
    public ResultadosTauUDTO execute(UUID uuid, boolean somenteCondicoesAdequadas) {
        var metricasJogoCrianca = obterTodasMetricasValidasCrianca(uuid, somenteCondicoesAdequadas);

        var taxasAcertos = obterListaDeAcertoPelasMetricas(metricasJogoCrianca);
        var taxasErros = obterListaDeErrosPelasMetricas(metricasJogoCrianca);
        var temposSessoes = obterListaDeTempoPelasMetricas(metricasJogoCrianca);

        var tauAcerto = calcularTauUPelaListaCompleta(taxasAcertos, true);
        var tauErro = calcularTauUPelaListaCompleta(taxasErros, true);
        var tauTempo = calcularTauUPelaListaCompleta(temposSessoes, true);

        return ResultadosTauUDTO.builder().TauUAcerto(tauAcerto).TauUErro(tauErro).TauUTempo(tauTempo).build();
    }

    public List<MetricasJogoModel> obterTodasMetricasValidasCrianca(UUID uuid, boolean somenteCondicoesAdequadas){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS)
                : metricasJogoRepository.searchAllByCriancaUUIDOrderById(uuid);
    }

    private double calcularTauUPelaListaCompleta(List<Double> listaCompleta, boolean maiorMelhor){
        if (listaCompleta.size() % 2 != 0) {
            listaCompleta = listaCompleta.subList(1, listaCompleta.size());
        }

        var meio = listaCompleta.size() / 2;
        var linhaDeBase = listaCompleta.subList(0, meio);
        var linhaDeIntervencao = listaCompleta.subList(meio, listaCompleta.size());

        try {
            if (maiorMelhor){
                return CalculadoraTauU.calcularTauU(linhaDeBase, linhaDeIntervencao);
            }
            return 0;
        }
        catch (Exception ex){
            return 0;
        }
    }

    private List<Double> obterListaDeAcertoPelasMetricas(List<MetricasJogoModel> metricasJogoDTOList){
        return metricasJogoDTOList.stream()
                .mapToDouble(MetricasJogoModel::getNumeroDeAcertos)
                .boxed()
                .toList();
    }

    private List<Double> obterListaDeErrosPelasMetricas(List<MetricasJogoModel> metricasJogoDTOList){
        return metricasJogoDTOList.stream()
                .mapToDouble(MetricasJogoModel::getNumeroDeErros)
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
