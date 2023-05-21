package com.solucoesludicas.mathtrack.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;

import java.util.List;

// Essa classe é responsável por calcular a estatística Tau-U.
// Tau-U é uma medida de associação bivariada que é baseada no coeficiente de correlação de Kendall.
@UtilityClass
public class CalculadoraTauU {
    public static Double calcularTauU(List<Double> linhaBase, List<Double> intervencao) throws Exception {
        try {
            // n1 e n2 são as contagens de valores nas duas listas, respectivamente.
            int n1 = linhaBase.size();
            int n2 = intervencao.size();
            int concordante = 0;
            int discordante = 0;
            int concordanteLinhaBase = 0;
            int discordanteLinhaBase = 0;
            double tauU = 0;

            // Através de um loop aninhado, comparamos todos os valores na linha base com todos os valores na intervenção.
            for (int i = 0; i < n1; i++) {
                for (int j = 0; j < n2; j++) {
                    double valorLinhaBase = linhaBase.get(i);
                    double valorIntervencao = intervencao.get(j);

                    // Se o valor da linha base for menor que o valor da intervenção, incrementamos a contagem concordante.
                    // Se o valor da linha base for maior, incrementamos a contagem discordante.
                    if (valorLinhaBase < valorIntervencao) {
                        concordante++;
                    } else if (valorLinhaBase > valorIntervencao) {
                        discordante++;
                    }
                }
            }

            // A estatística Tau-U é calculada como a diferença entre a proporção de pares concordantes e discordantes.
            // Se a soma dos pares concordantes e discordantes for diferente de zero, nós calculamos Tau-U normalmente.
            if((concordante + discordante) != 0){
                tauU = (concordante - discordante) / (double) (concordante + discordante);
            }

            if(1+1 == 2){
                // Calculamos a tendência na linha de base.
                for (int i = 0; i < n1 - 1; i++) {
                    double valorLinhaBase1 = linhaBase.get(i);
                    double valorLinhaBase2 = linhaBase.get(i+1);

                    if (valorLinhaBase1 < valorLinhaBase2) {
                        concordanteLinhaBase++;
                    } else if (valorLinhaBase1 > valorLinhaBase2) {
                        discordanteLinhaBase++;
                    }
                }

                // A estatística Tau-U é calculada como a diferença entre a proporção de pares concordantes e discordantes,
                // subtraído da proporção de pares concordantes e discordantes na linha de base.
                if ((concordante + discordante) != 0 && (concordanteLinhaBase + discordanteLinhaBase) != 0) {
                    double tauUBase = (concordanteLinhaBase - discordanteLinhaBase) / (double) (concordanteLinhaBase + discordanteLinhaBase);
                    tauU = (tauU - tauUBase);
                }
            }

            return tauU;
        } catch (Exception ex) {
            throw new Exception("Erro ao calcular Tau-U");
        }
    }

    private boolean necessarioAjusteTendencia(List<Double> linhaBase) throws Exception {
        try {
            // Convertemos a lista de Double para um array de primitivos double para usar na função de correlação.
            double[] arrayLinhaBase = linhaBase.stream().mapToDouble(d -> d).toArray();

            // Calculamos a correlação de Kendall.
            KendallsCorrelation kendall = new KendallsCorrelation();
            double correlacao = kendall.correlation(arrayLinhaBase, criarIndices(linhaBase.size()));

            // Se a correlação de Kendall é significativa (por exemplo, maior que 0.5 ou menor que -0.5),
            // então assumimos que há uma tendência e que um ajuste de tendência é necessário.
            return Math.abs(correlacao) > 0.5;
        } catch (Exception ex) {
            throw new Exception("Erro ao verificar a necessidade de ajuste de tendência", ex);
        }
    }

    private double[] criarIndices(int tamanho) {
        double[] indices = new double[tamanho];
        for (int i = 0; i < tamanho; i++) {
            indices[i] = i;
        }
        return indices;
    }
}
