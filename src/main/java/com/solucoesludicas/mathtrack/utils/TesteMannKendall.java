package com.solucoesludicas.mathtrack.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

@UtilityClass
public class TesteMannKendall {
    // Nível de significância para o teste estatístico
    private static final double alpha = 0.05;

    // Criação da distribuição normal para cálculo do valor-p
    private static final NormalDistribution distribuicaoNormal = new NormalDistribution();

    // Método que verifica a presença de uma tendência na lista de dados fornecida
    public static boolean possuiTendenciaMannKendall(List<Double> dados) {
        // Converte a lista de dados em um array para o cálculo da correlação de Kendall
        double[] arrayDados = dados.stream().mapToDouble(Double::doubleValue).toArray();
        // Criação da correlação de Kendall
        KendallsCorrelation correlacaoKendall = new KendallsCorrelation();
        // Calcula o valor de tau (correlação de Kendall)
        double tau = correlacaoKendall.correlation(arrayDados, criarArrayIndices(dados.size()));

        // Calcula o valor de S (indicador do sinal de tau)
        int s = (int) Math.signum(tau);
        // Calcula a variância de S
        double varS = (dados.size() * (dados.size() - 1) * (2 * dados.size() + 5)) / 18.0;

        // Calcula o valor de Z usando S e varS
        double z;
        if (s > 0) {
            z = (s - 1) / Math.sqrt(varS);
        } else if (s == 0) {
            z = 0;
        } else {
            z = (s + 1) / Math.sqrt(varS);
        }

        // Calcula o valor-p usando a distribuição normal
        double p = 2 * (1 - distribuicaoNormal.cumulativeProbability(Math.abs(z)));
        // Retorna verdadeiro se o valor-p for menor que o nível de significância (indicando a presença de uma tendência)
        return p < alpha;
    }

    // Cria um array de índices correspondente aos índices dos dados fornecidos
    private static double[] criarArrayIndices(int tamanho) {
        double[] indices = new double[tamanho];
        for (int i = 0; i < tamanho; i++) {
            indices[i] = i;
        }
        return indices;
    }
}
