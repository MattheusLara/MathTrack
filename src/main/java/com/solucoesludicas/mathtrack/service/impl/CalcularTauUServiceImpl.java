package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
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
    public ResultadosTauUDTO execute(UUID criancaUuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, int dificuldade, PlataformaEnum plataforma) {
        var metricasJogoCrianca = obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(criancaUuid, somenteCondicoesAdequadas, habilidadeTrabalhada, dificuldade, plataforma);

        double tauAcerto;
        double tauErro;
        double tauTempo;

        var taxasAcertos = obterListaDeAcertoPelasMetricas(metricasJogoCrianca);
        var taxasErros = obterListaDeErrosPelasMetricas(metricasJogoCrianca);
        var temposSessoes = obterListaDeTempoPelasMetricas(metricasJogoCrianca);

        if(metricasJogoCrianca.size() <= 20) {
            tauAcerto = calcularTauUPelaListaDividida(taxasAcertos, true);
            tauErro = calcularTauUPelaListaDividida(taxasErros, false);
            tauTempo = calcularTauUPelaListaDividida(temposSessoes, false);
        }
        else {
            tauAcerto = calcularTauUPelaListaCompleta(taxasAcertos, true);
            tauErro = calcularTauUPelaListaCompleta(taxasErros, false);
            tauTempo = calcularTauUPelaListaCompleta(temposSessoes, false);
        }

        return ResultadosTauUDTO.builder().tauUAcerto(tauAcerto).tauUErro(tauErro).tauUTempo(tauTempo).build();
    }

    private List<MetricasJogoModel> obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(UUID uuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade, PlataformaEnum plataforma){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS, habilidadeTrabalhada, plataforma, dificuldade)
                : metricasJogoRepository.searchAllByCriancaUUIDAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, habilidadeTrabalhada, plataforma, dificuldade);
    }

    private double calcularTauUPelaListaDividida(List<Double> listaCompleta, boolean maiorMelhor){
        var meio = listaCompleta.size() / 2;
        var linhaDeBase = listaCompleta.subList(0, meio);
        var linhaDeIntervencao = listaCompleta.subList(meio, listaCompleta.size());

        try {
            return CalculadoraTauU.calcularTauU(linhaDeBase, linhaDeIntervencao, maiorMelhor);
        }
        catch (Exception ex){
            return 0;
        }
    }

    private double calcularTauUPelaListaCompleta(List<Double> listaCompleta, boolean maiorMelhor){
        var linhaDeBase = listaCompleta.subList(0, 10);
        var linhaDeIntervencao = listaCompleta.subList(10, listaCompleta.size());

        try {
            return CalculadoraTauU.calcularTauU(linhaDeBase, linhaDeIntervencao, maiorMelhor);
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
