package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.CalcularTauUService;
import com.solucoesludicas.mathtrack.utils.CalculadoraTauU;
import com.solucoesludicas.mathtrack.utils.LinhaDeBaseEIntervencaoTauU;
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
    public ResultadosTauUDTO execute(UUID criancaUuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, int dificuldade, PlataformaEnum plataforma) throws Exception {
        var metricasJogoCrianca = obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(criancaUuid, somenteCondicoesAdequadas, habilidadeTrabalhada, dificuldade, plataforma);

        double tauAcerto;
        double tauErro;
        double tauTempo;

        var metricasAcertos = obterListaDeAcertoPelasMetricas(metricasJogoCrianca);
        var metricasErros = obterListaDeErrosPelasMetricas(metricasJogoCrianca);
        var metricasSessoes = obterListaDeTempoPelasMetricas(metricasJogoCrianca);


        tauAcerto = calcularTauU(metricasAcertos, true);
        tauErro = calcularTauU(metricasErros, false);
        tauTempo = calcularTauU(metricasSessoes, false);

        return ResultadosTauUDTO.builder().tauUAcerto(tauAcerto).tauUErro(tauErro).tauUTempo(tauTempo).build();
    }

    private double calcularTauU(List<Double> metricas, boolean maiorMelhor) throws Exception {
        var baseEIntervencao = LinhaDeBaseEIntervencaoTauU.obterLinhaDeBaseEIntervencao(metricas);

        return CalculadoraTauU.calcularTauU(baseEIntervencao.getLinhaDeBase(), baseEIntervencao.getIntervencao(), maiorMelhor);
    }

    private List<MetricasJogoModel> obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(UUID uuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade, PlataformaEnum plataforma){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS, habilidadeTrabalhada, plataforma, dificuldade)
                : metricasJogoRepository.searchAllByCriancaUUIDAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, habilidadeTrabalhada, plataforma, dificuldade);
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
