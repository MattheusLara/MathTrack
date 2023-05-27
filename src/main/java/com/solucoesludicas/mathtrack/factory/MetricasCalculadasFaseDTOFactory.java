package com.solucoesludicas.mathtrack.factory;

import com.solucoesludicas.mathtrack.dto.MetricasCalculadasFaseDTO;
import com.solucoesludicas.mathtrack.dto.ResultadosMetricasCalculadasDTO;
import com.solucoesludicas.mathtrack.enums.PlataformaEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class MetricasCalculadasFaseDTOFactory {

    public MetricasCalculadasFaseDTO montarMetricasCalculadasDTO(ResultadosMetricasCalculadasDTO resultadosRelatorioCondAdequadasDTO, ResultadosMetricasCalculadasDTO resultadosRelatorioCondNaoAdequadasDTO, PlataformaEnum plataforma) {
        if(resultadosRelatorioCondAdequadasDTO != null && resultadosRelatorioCondNaoAdequadasDTO != null){
            return montarMetricasCalculadasCompletas(resultadosRelatorioCondAdequadasDTO, resultadosRelatorioCondNaoAdequadasDTO, plataforma);
        } else if (resultadosRelatorioCondNaoAdequadasDTO != null) {
            return montarMetricasCalculadasSemAdequadas(resultadosRelatorioCondNaoAdequadasDTO, plataforma);
        }
        else{
            return null;
        }
    }

    public MetricasCalculadasFaseDTO montarMetricasCalculadasCompletas(ResultadosMetricasCalculadasDTO condAdequadas, ResultadosMetricasCalculadasDTO condNaoAdequadas, PlataformaEnum plataforma)
    {
        return MetricasCalculadasFaseDTO.builder()
                .criancaUUID(condAdequadas.getCriancaUUID())
                .dificuldade(condAdequadas.getDificuldade())
                .habilidadeTrabalhada(condAdequadas.getHabilidadeTrabalhada())
                .plataforma(plataforma)
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

    public MetricasCalculadasFaseDTO montarMetricasCalculadasSemAdequadas(ResultadosMetricasCalculadasDTO condNaoAdequadas, PlataformaEnum plataforma)
    {
        return MetricasCalculadasFaseDTO.builder()
                .criancaUUID(condNaoAdequadas.getCriancaUUID())
                .dificuldade(condNaoAdequadas.getDificuldade())
                .habilidadeTrabalhada(condNaoAdequadas.getHabilidadeTrabalhada())
                .plataforma(plataforma)
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
