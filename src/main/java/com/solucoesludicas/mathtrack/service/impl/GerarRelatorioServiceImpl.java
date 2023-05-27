package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosRelatorioDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.factory.MetricasCalculadasFaseDTOFactory;
import com.solucoesludicas.mathtrack.models.MetricasCalculadasModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.GerarRelatorioService;
import com.solucoesludicas.mathtrack.service.SalvarMetricasCalculadasFaseService;
import com.solucoesludicas.mathtrack.utils.CalculadoraTauU;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GerarRelatorioServiceImpl implements GerarRelatorioService {

    private final MetricasJogoRepository metricasJogoRepository;
    private final SalvarMetricasCalculadasFaseService salvarMetricasCalculadasFaseService;
    private final MetricasCalculadasFaseDTOFactory metricasCalculadasFaseDTOFactory;

    @Override
    public String gerarRelatorioGeral(UUID criancaUuid) throws Exception {
        var habilidades = metricasJogoRepository.findDistinctHabilidadeTrabalhadaByCriancaUUID(criancaUuid);

        if(habilidades.size() == 0){
            throw new Exception("Nao existem metricas suficientes para esta crianca");
        }

        for(HabilidadeEnum habilidadeTrabalhada: habilidades){
            var dificuldades = metricasJogoRepository.findDistinctDificuldadeDeFasesByCriancaUUIDAndHabilidadeTrabalhada(criancaUuid, habilidadeTrabalhada);

            for(Integer dificuldade : dificuldades) {
                var relatorioCondAdequadas = calcularRelatorio(criancaUuid, true, habilidadeTrabalhada, dificuldade);
                var relatorioCondNaoAdequadas = calcularRelatorio(criancaUuid, false, habilidadeTrabalhada, dificuldade);

                var metricasCalculadasFaseDTO = metricasCalculadasFaseDTOFactory.montarMetricasCalculadasDTO(relatorioCondAdequadas, relatorioCondNaoAdequadas);

                if (salvarMetricasCalculadas(metricasCalculadasFaseDTO) == null) {
                    return null;
                }
            }
        }

        return "Link do relatorio: ";
    }

    @Override
    public String gerarRelatorioHabilidade(UUID criancaUui, HabilidadeEnum habilidadeTrabalhada) {
        return null;
    }

    private ResultadosRelatorioDTO calcularRelatorio(UUID criancaUuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade) throws Exception {
        var metricasJogoCrianca = obterTodasMetricasValidasCriancaPorHabilidadEDificuldade(criancaUuid, somenteCondicoesAdequadas, habilidadeTrabalhada, dificuldade);

        if(metricasJogoCrianca.size() >= 2){
            var numeroAcertos = obterListaDeAcertoPelasMetricas(metricasJogoCrianca);
            var numeroErros = obterListaDeErrosPelasMetricas(metricasJogoCrianca);
            var temposSessoes = obterListaDeTempoPelasMetricas(metricasJogoCrianca);

            var tauU = calcularTauU(numeroAcertos, numeroErros, metricasJogoCrianca.size());
            var tauUAcerto = tauU.getTauUAcerto();
            var tauUErro = tauU.getTauUErro();


            var mediaNumeroDeAcertos = calcularMedia(numeroAcertos);
            var mediaNumeroDeErros = calcularMedia(numeroErros);
            var mediaTempoSessoes = calcularMedia(temposSessoes);

            return ResultadosRelatorioDTO.builder()
                    .criancaUUID(criancaUuid)
                    .habilidadeTrabalhada(habilidadeTrabalhada)
                    .dificuldade(dificuldade)
                    .tauUAcerto(tauUAcerto)
                    .tauUErro(tauUErro)
                    .classificacaoTauUAcertos(ClassificacaoTauUEnum.obterClassificacao(tauUAcerto))
                    .classificacaoTauUErros(ClassificacaoTauUEnum.obterClassificacao(tauUErro))
                    .mediaDeAcerto(mediaNumeroDeAcertos)
                    .mediaDeErros(mediaNumeroDeErros)
                    .mediaDeTempo(mediaTempoSessoes)
                    .build();
        }
        else{
            System.out.println((String.format("Quantidade de metricas insuficientes para crianca: %s, na habilidade: %s e dificuldade: %d", criancaUuid, habilidadeTrabalhada.toString(), dificuldade)));
            return null;
        }
    }

    private List<MetricasJogoModel> obterTodasMetricasValidasCriancaPorHabilidadEDificuldade(UUID uuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasAndHabilidadeTrabalhadaAndDificuldadeDaFaseOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS, habilidadeTrabalhada, dificuldade)
                : metricasJogoRepository.searchAllByCriancaUUIDAndHabilidadeTrabalhadaAndDificuldadeDaFaseOrderById(uuid, habilidadeTrabalhada, dificuldade);
    }

    private double calcularMedia(List<Double> valores){
        DescriptiveStatistics estatisticas = new DescriptiveStatistics();

        for (double valor : valores) {
            estatisticas.addValue(valor);
        }

        return estatisticas.getMean();
    }

    private ResultadosTauUDTO calcularTauU(List<Double> numeroAcertos, List<Double> numeroErros, int quantidadeDeMetricas) {
        double tauAcerto;
        double tauErro;

        if(quantidadeDeMetricas <= 20) {
            tauAcerto = calcularTauUPelaListaDividida(numeroAcertos, true);
            tauErro = calcularTauUPelaListaDividida(numeroErros, false);
        }
        else {
            tauAcerto = calcularTauUPelaListaCompleta(numeroAcertos, true);
            tauErro = calcularTauUPelaListaCompleta(numeroErros, false);
        }

        return ResultadosTauUDTO.builder().tauUAcerto(tauAcerto).tauUErro(tauErro).build();
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

    private MetricasCalculadasModel salvarMetricasCalculadas(MetricasCalculadasFaseDTO metricasCalculadasFaseDTO){
        return salvarMetricasCalculadasFaseService.execute(metricasCalculadasFaseDTO);
    }
}
