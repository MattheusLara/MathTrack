package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosMetricasCalculadasDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GerarRelatorioServiceImpl implements GerarRelatorioService {

    private final MetricasJogoRepository metricasJogoRepository;
    private final SalvarMetricasCalculadasFaseService salvarMetricasCalculadasFaseService;
    private final MetricasCalculadasFaseDTOFactory metricasCalculadasFaseDTOFactory;
    private final Logger logger;

    @Override
    public String gerarRelatorioGeral(UUID criancaUuid, PlataformaEnum plataforma) throws Exception {
        var horarioInicioGeracaoDeMetricas = LocalDateTime.now(ZoneId.of("UTC"));

        calcularMetricasParaRelatorioGeral(criancaUuid, plataforma);


        return String.format("Link do relatorio: %s", horarioInicioGeracaoDeMetricas);
    }

    @Override
    public String gerarRelatorioHabilidade(UUID criancaUui, HabilidadeEnum habilidadeTrabalhada) {
        return null;
    }

    public void calcularMetricasParaRelatorioGeral(UUID criancaUuid, PlataformaEnum plataforma) throws Exception {
        var habilidades = metricasJogoRepository.findDistinctHabilidadeTrabalhadaByCriancaUUID(criancaUuid, plataforma);

        if(habilidades.isEmpty()){
            throw new Exception("Nao existem metricas suficientes para esta crianca");
        }

        for(HabilidadeEnum habilidadeTrabalhada: habilidades){
            var dificuldades = metricasJogoRepository.findDistinctDificuldadeDeFasesByCriancaUUIDAndHabilidadeTrabalhada(criancaUuid, habilidadeTrabalhada, plataforma);

            for(Integer dificuldade : dificuldades) {
                var relatorioCondAdequadas = calcularMetricas(criancaUuid, true, habilidadeTrabalhada, dificuldade, plataforma);
                var relatorioCondNaoAdequadas = calcularMetricas(criancaUuid, false, habilidadeTrabalhada, dificuldade, plataforma);

                var metricasCalculadasFaseDTO = metricasCalculadasFaseDTOFactory.montarMetricasCalculadasDTO(relatorioCondAdequadas, relatorioCondNaoAdequadas, plataforma);

                if(metricasCalculadasFaseDTO != null){
                    var resultadoSalvarMetricasCalculadas = salvarMetricasCalculadas(metricasCalculadasFaseDTO);

                    if (resultadoSalvarMetricasCalculadas == null) {
                        logger.info(String.format("Nao foi possivel salvar as metricas para crianca: %s, na habilidade: %s e dificuldade: %d", criancaUuid, habilidadeTrabalhada.toString(), dificuldade));
                    }
                }
                else {
                    logger.info((String.format("Quantidade de metricas insuficientes para crianca: %s, na habilidade: %s e dificuldade: %d", criancaUuid, habilidadeTrabalhada.toString(), dificuldade)));
                }
            }
        }
    }

    private ResultadosMetricasCalculadasDTO calcularMetricas(UUID criancaUuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade, PlataformaEnum plataforma) {
        var metricasJogoCrianca = obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(criancaUuid, somenteCondicoesAdequadas, habilidadeTrabalhada, dificuldade, plataforma);

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

            return ResultadosMetricasCalculadasDTO.builder()
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
            return null;
        }
    }

    private List<MetricasJogoModel> obterTodasMetricasValidasCriancaPorHabilidadEDificuldadeEPlataforma(UUID uuid, boolean somenteCondicoesAdequadas, HabilidadeEnum habilidadeTrabalhada, Integer dificuldade, PlataformaEnum plataforma){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS, habilidadeTrabalhada, plataforma, dificuldade)
                : metricasJogoRepository.searchAllByCriancaUUIDAndHabilidadeTrabalhadaAndPlataformaAndDificuldadeDaFaseOrderById(uuid, habilidadeTrabalhada, plataforma, dificuldade);
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
