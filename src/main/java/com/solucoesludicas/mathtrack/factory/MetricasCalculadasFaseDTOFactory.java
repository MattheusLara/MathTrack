package com.solucoesludicas.mathtrack.factory;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosRelatorioDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class MetricasCalculadasFaseDTOFactory {

    public MetricasCalculadasFaseDTO montarMetricasCalculadasDTO(ResultadosRelatorioDTO resultadosRelatorioCondAdequadasDTO, ResultadosRelatorioDTO resultadosRelatorioCondNaoAdequadasDTO) throws Exception {
        if(resultadosRelatorioCondAdequadasDTO != null && resultadosRelatorioCondNaoAdequadasDTO != null){
            return montarMetricasCalculadasCompletas(resultadosRelatorioCondAdequadasDTO, resultadosRelatorioCondNaoAdequadasDTO);
        } else if (resultadosRelatorioCondNaoAdequadasDTO != null) {
            return montarMetricasCalculadasSemAdequadas(resultadosRelatorioCondNaoAdequadasDTO);
        }
        else{
            throw new Exception("Erro ao montar MetricasCalculadasDTO");
        }
    }

    public MetricasCalculadasFaseDTO montarMetricasCalculadasCompletas(ResultadosRelatorioDTO condAdequadas, ResultadosRelatorioDTO condNaoAdequadas)
    {
        return MetricasCalculadasFaseDTO.builder()
                .criancaUUID(condAdequadas.getCriancaUUID())
                .dificuldade(condAdequadas.getDificuldade())
                .habilidadeTrabalhada(condAdequadas.getHabilidadeTrabalhada())
                .mediaAcertosCondAdequadas(condAdequadas.getMediaDeAcerto())
                .mediaErrosCondAdequadas(condAdequadas.getMediaDeErros())
                .mediaTempoCondAdequadas(condAdequadas.getMediaDeTempo())
                .tauUAcertosCondAdequadas(condAdequadas.getTauUAcerto())
                .tauUErrosCondAdequadas(condAdequadas.getTauUErro())
                .classificacaoTauUAcertosCondAdequadas(condAdequadas.getClassificacaoTauUAcertos())
                .classificacaoTauUErrosCondAdequadas(condAdequadas.getClassificacaoTauUErros())
                .mediaAcertosCondNaoAdequadas(condNaoAdequadas.getMediaDeAcerto())
                .mediaErrosCondNaoAdequadas(condNaoAdequadas.getMediaDeErros())
                .mediaTempoCondNaoAdequadas(condNaoAdequadas.getMediaDeTempo())
                .tauUAcertosCondNaoAdequadas(condNaoAdequadas.getTauUAcerto())
                .tauUErrosCondNaoAdequadas(condNaoAdequadas.getTauUErro())
                .classificacaoTauUAcertosCondNaoAdequadas(condNaoAdequadas.getClassificacaoTauUAcertos())
                .classificacaoTauUErrosCondNaoAdequadas(condNaoAdequadas.getClassificacaoTauUErros())
                .dataCalculoMetricas(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    public MetricasCalculadasFaseDTO montarMetricasCalculadasSemAdequadas(ResultadosRelatorioDTO condNaoAdequadas)
    {
        return MetricasCalculadasFaseDTO.builder()
                .criancaUUID(condNaoAdequadas.getCriancaUUID())
                .dificuldade(condNaoAdequadas.getDificuldade())
                .habilidadeTrabalhada(condNaoAdequadas.getHabilidadeTrabalhada())
                .mediaAcertosCondNaoAdequadas(condNaoAdequadas.getMediaDeAcerto())
                .mediaErrosCondNaoAdequadas(condNaoAdequadas.getMediaDeErros())
                .mediaTempoCondNaoAdequadas(condNaoAdequadas.getMediaDeTempo())
                .tauUAcertosCondNaoAdequadas(condNaoAdequadas.getTauUAcerto())
                .tauUErrosCondNaoAdequadas(condNaoAdequadas.getTauUErro())
                .classificacaoTauUAcertosCondNaoAdequadas(condNaoAdequadas.getClassificacaoTauUAcertos())
                .classificacaoTauUErrosCondNaoAdequadas(condNaoAdequadas.getClassificacaoTauUErros())
                .dataCalculoMetricas(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
    }

}
