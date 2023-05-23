package com.solucoesludicas.mathtrack.service.impl;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosRelatorioDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosTauUDTO;
import com.solucoesludicas.mathtrack.enums.ClassificacaoTauUEnum;
import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.models.MetricasCalculadasFaseModel;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import com.solucoesludicas.mathtrack.repository.MetricasJogoRepository;
import com.solucoesludicas.mathtrack.service.GerarRelatorioJogoService;
import com.solucoesludicas.mathtrack.service.SalvarMetricasCalculadasFaseService;
import com.solucoesludicas.mathtrack.utils.CalculadoraTauU;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GerarRelatorioJogoServiceImpl implements GerarRelatorioJogoService {

    private final MetricasJogoRepository metricasJogoRepository;
    private final SalvarMetricasCalculadasFaseService salvarMetricasCalculadasFaseService;

    @Override
    public String execute(UUID criancaUuid, Long idJogo){
        var numerosFases = metricasJogoRepository.findDistinctNumeroDaFaseByJogoIDAndCriancaUUID(idJogo, criancaUuid);

        for(Integer numeroFase: numerosFases){
            var relatorioCondAdequadas = calcularRelatorio(criancaUuid, true, idJogo, numeroFase);
            var relatorioCondNaoAdequadas = calcularRelatorio(criancaUuid, false, idJogo, numeroFase);

            var metricasCalculadasFaseDTO = montarMetricasCalculadasDTO(relatorioCondAdequadas, relatorioCondNaoAdequadas);

            if(salvarMetricasCalculadas(metricasCalculadasFaseDTO) == null){
                return null;
            }
        }

        return "Link do relatorio: ";
    }

    private ResultadosRelatorioDTO calcularRelatorio(UUID criancaUuid, boolean somenteCondicoesAdequadas, Long idJogo, int numeroDaFase) {
        var metricasJogoCrianca = obterTodasMetricasValidasJogoEFaseCrianca(criancaUuid, somenteCondicoesAdequadas, idJogo, numeroDaFase);

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
                .jogoID(idJogo)
                .numeroDaFase(numeroDaFase)
                .dificuldadeDaFase(metricasJogoCrianca.get(0) != null ? metricasJogoCrianca.get(0).getDificuldadeDaFase() : 0)
                .tauUAcerto(tauUAcerto)
                .tauUErro(tauUErro)
                .classificacaoTauUAcertos(ClassificacaoTauUEnum.obterClassificacao(tauUAcerto))
                .classificacaoTauUErros(ClassificacaoTauUEnum.obterClassificacao(tauUErro))
                .mediaDeAcerto(mediaNumeroDeAcertos)
                .mediaDeErros(mediaNumeroDeErros)
                .mediaDeTempo(mediaTempoSessoes)
                .build();
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

    private List<MetricasJogoModel> obterTodasMetricasValidasJogoEFaseCrianca(UUID uuid, boolean somenteCondicoesAdequadas, Long idJogo, int numeroDaFase){
        return somenteCondicoesAdequadas ?
                metricasJogoRepository.searchAllByCriancaUUIDAndCondicoesAdequadasAndJogoIDAndNumeroDaFaseOrderById(uuid, CondicoesAdequadasEnum.COND_ADEQUADAS, idJogo, numeroDaFase)
                : metricasJogoRepository.searchAllByCriancaUUIDAndJogoIDAndNumeroDaFaseOrderById(uuid, idJogo, numeroDaFase);
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

    private MetricasCalculadasFaseDTO montarMetricasCalculadasDTO(ResultadosRelatorioDTO resultadosRelatorioCondAdequadasDTO, ResultadosRelatorioDTO resultadosRelatorioCondNaoAdequadasDTO)
    {
        return MetricasCalculadasFaseDTO.builder()
                .criancaUUID(resultadosRelatorioCondAdequadasDTO.getCriancaUUID())
                .jogoID(resultadosRelatorioCondAdequadasDTO.getJogoID())
                .numeroDaFase(resultadosRelatorioCondAdequadasDTO.getNumeroDaFase())
                .dificuldadeDaFase(resultadosRelatorioCondAdequadasDTO.getDificuldadeDaFase())
                .mediaAcertosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getMediaDeAcerto())
                .mediaErrosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getMediaDeErros())
                .mediaTempoCondAdequadas(resultadosRelatorioCondAdequadasDTO.getMediaDeTempo())
                .tauUAcertosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getTauUAcerto())
                .tauUErrosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getTauUErro())
                .classificacaoTauUAcertosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getClassificacaoTauUAcertos())
                .classificacaoTauUErrosCondAdequadas(resultadosRelatorioCondAdequadasDTO.getClassificacaoTauUErros())
                .mediaAcertosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getMediaDeAcerto())
                .mediaErrosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getMediaDeErros())
                .mediaTempoCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getMediaDeTempo())
                .tauUAcertosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getTauUAcerto())
                .tauUErrosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getTauUErro())
                .classificacaoTauUAcertosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getClassificacaoTauUAcertos())
                .classificacaoTauUErrosCondNaoAdequadas(resultadosRelatorioCondNaoAdequadasDTO.getClassificacaoTauUErros())
                .dataCalculoMetricas(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    private MetricasCalculadasFaseModel salvarMetricasCalculadas(MetricasCalculadasFaseDTO metricasCalculadasFaseDTO){
        return salvarMetricasCalculadasFaseService.execute(metricasCalculadasFaseDTO);
    }
}
