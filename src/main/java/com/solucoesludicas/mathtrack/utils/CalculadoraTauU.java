package com.solucoesludicas.mathtrack.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;

// Essa classe é responsável por calcular a estatística Tau-U.
// Tau-U é uma medida de associação bivariada que é baseada no coeficiente de correlação de Kendall.
@UtilityClass
public class CalculadoraTauU {
    // Este método recebe duas listas de valores (linha base e intervenção) e retorna o valor calculado da estatística Tau-U.
    public static Double calcularTauU(List<Double> linhaBase, List<Double> intervencao) throws Exception {
        try {
            // n1 e n2 são as contagens de valores nas duas listas, respectivamente.
            int n1 = linhaBase.size();
            int n2 = intervencao.size();
            int concordante = 0;
            int discordante = 0;
            double tauU = 0;
            double ajusteTendencia;

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

            // Ajustes na estatística Tau-U para tendência e sobreposição.
            if(saoLineares(linhaBase, intervencao)){
                ajusteTendencia = calcularAjusteTendenciaLinear(linhaBase, intervencao);
            }
            else{
                ajusteTendencia = calcularAjusteTendenciaNaoLinear(linhaBase, intervencao);
            }

            //tauU -= ajusteTendencia;

            double ajusteSobreposicao = calcularSobreposicao(linhaBase, intervencao);
            //tauU -= ajusteSobreposicao;

            // Retornamos o valor final da estatística Tau-U.
            return tauU;
        } catch (Exception ex) {
            throw new Exception("Erro ao calcular Tau-U");
        }
    }

    // Este método é responsável por calcular o ajuste de tendência linear.
    // Ele usa regressão linear simples para calcular as inclinações das linhas de melhor ajuste para os dois conjuntos de dados e retorna a diferença entre elas.
    private double calcularAjusteTendenciaLinear(List<Double> linhaBase, List<Double> intervencao) {
        SimpleRegression regressaoLinhaBase = new SimpleRegression();
        for (int i = 0; i < linhaBase.size(); i++) {
            regressaoLinhaBase.addData(i, linhaBase.get(i));
        }

        SimpleRegression regressaoIntervencao = new SimpleRegression();
        for (int i = 0; i < intervencao.size(); i++) {
            regressaoIntervencao.addData(i, intervencao.get(i));
        }

        // O coeficiente de tendência é a diferença entre os coeficientes lineares
        double ajusteTendencia = regressaoLinhaBase.getSlope() - regressaoIntervencao.getSlope();

        // Normalização do ajuste de tendência
        ajusteTendencia = ajusteTendencia / (linhaBase.size() * intervencao.size());

        return ajusteTendencia;
    }

    // Este método é responsável por calcular o ajuste de tendência não-linear.
    // Ele usa ajuste de curva polinomial para calcular os coeficientes do polinômio de segundo grau que melhor se ajusta aos dois conjuntos de dados e retorna a diferença entre os coeficientes lineares.
    private double calcularAjusteTendenciaNaoLinear(List<Double> linhaBase, List<Double> intervencao) {
        PolynomialCurveFitter ajustador = PolynomialCurveFitter.create(2);  // Ajuste polinomial de 2º grau

        WeightedObservedPoints pontosLinhaBase = new WeightedObservedPoints();
        for (int i = 0; i < linhaBase.size(); i++) {
            pontosLinhaBase.add(i, linhaBase.get(i));
        }
        double[] coeficientesLinhaBase = ajustador.fit(pontosLinhaBase.toList());

        WeightedObservedPoints pontosIntervencao = new WeightedObservedPoints();
        for (int i = 0; i < intervencao.size(); i++) {
            pontosIntervencao.add(i, intervencao.get(i));
        }
        double[] coeficientesIntervencao = ajustador.fit(pontosIntervencao.toList());

        // O coeficiente de tendência é a diferença entre os coeficientes lineares
        double ajusteTendencia = coeficientesLinhaBase[1] - coeficientesIntervencao[1];

        // Normalização do ajuste de tendência
        ajusteTendencia = ajusteTendencia / (linhaBase.size() * intervencao.size());

        return ajusteTendencia;
    }

    //Verifica se os dados da linha de base e intervensao sao lineares ou nao
    private boolean saoLineares(List<Double> linhaBase, List<Double> intervencao) {
        SimpleRegression regressaoLinhaBase = new SimpleRegression();
        SimpleRegression regressaoIntervencao = new SimpleRegression();

        for (int i = 0; i < linhaBase.size(); i++) {
            regressaoLinhaBase.addData(i, linhaBase.get(i));
        }

        for (int i = 0; i < intervencao.size(); i++) {
            regressaoIntervencao.addData(i, intervencao.get(i));
        }

        // Verifica se R² está próximo de 1 para ambos os conjuntos de dados.
        double limiar = 0.95;  // O valor do limiar pode ser ajustado de acordo com a necessidade.
        return (regressaoLinhaBase.getRSquare() > limiar && regressaoIntervencao.getRSquare() > limiar);
    }

    // Este método é responsável por calcular a sobreposição entre os dois conjuntos de dados.
    // Ele determina a quantidade de valores na linha base que caem dentro do intervalo de valores na intervenção e vice-versa.
    private double calcularSobreposicao(List<Double> linhaBase, List<Double> intervencao) {
        DescriptiveStatistics estatisticasLinhaBase = new DescriptiveStatistics();
        for (Double valor : linhaBase) {
            estatisticasLinhaBase.addValue(valor);
        }

        DescriptiveStatistics estatisticasIntervencao = new DescriptiveStatistics();
        for (Double valor : intervencao) {
            estatisticasIntervencao.addValue(valor);
        }

        double minimoLinhaBase = estatisticasLinhaBase.getMin();
        double maximoLinhaBase = estatisticasLinhaBase.getMax();

        double minimoIntervencao = estatisticasIntervencao.getMin();
        double maximoIntervencao = estatisticasIntervencao.getMax();

        int contagemSobreposicao = 0;
        for (Double valor : linhaBase) {
            if (valor >= minimoIntervencao && valor <= maximoIntervencao) {
                contagemSobreposicao++;
            }
        }

        for (Double valor : intervencao) {
            if (valor >= minimoLinhaBase && valor <= maximoLinhaBase) {
                contagemSobreposicao++;
            }
        }

        double totalPontos = (linhaBase.size() + intervencao.size());

        return contagemSobreposicao / totalPontos;
    }
}
