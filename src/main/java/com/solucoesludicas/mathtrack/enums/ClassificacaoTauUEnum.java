package com.solucoesludicas.mathtrack.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassificacaoTauUEnum {
    EVOLUCAO_EXPRESSIVA(0.8, 1.0, "Ótima evolução"),
    EVOLUCAO_MODERADA(0.5, 0.8, "Moderada a substancial evolução"),
    EVOLUCAO_LEVE(0.3, 0.5, "Média ou boa evolução"),
    EVOLUCAO_MINIMA(0, 0.3, "Muito pouca ou nenhuma evolução"),
    REGRESSAO_MINIMA(-0.3, 0, "Regressão leve ou ausente"),
    REGRESSAO_LEVE(-0.5, -0.3, "Regressão leve a moderada"),
    REGRESSAO_MODERADA(-0.8, -0.5, "Regressão moderada a substancial"),
    REGRESSAO_EXPRESSIVA(-1.0, -0.8, "Regressão expressiva");

    private double limiteInferior;
    private double limiteSuperior;
    private String descricao;

    public static ClassificacaoTauUEnum obterClassificacao(double valor) {
        for (ClassificacaoTauUEnum classificacao : values()) {
            if (valor >= classificacao.limiteInferior && valor < classificacao.limiteSuperior) {
                return classificacao;
            }
        }
        throw new IllegalArgumentException("Valor de classificação Tau-U fora dos limites.");
    }

    public String obterDescricao() {
        return descricao;
    }
}
