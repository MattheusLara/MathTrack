package com.solucoesludicas.mathtrack.utils;

import com.solucoesludicas.mathtrack.dto.LinhaDeBaseEIntervencaoObject;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class LinhaDeBaseEIntervencaoTauU {
    public LinhaDeBaseEIntervencaoObject obterLinhaDeBaseEIntervencao(List<Double> listaCompleta){
        var quantidadeDeMetricas =  listaCompleta.size();

        var linhaDeBaseEIntervencao = obterMelhorLinhaDeBaseEIntervao(listaCompleta, quantidadeDeMetricas);

        if(linhaDeBaseEIntervencao != null){
            return linhaDeBaseEIntervencao;
        }
        else if(quantidadeDeMetricas <= 20) {
            return obterPelaListaDividida(listaCompleta);
        }
        else {
            return obterSendoOs10PrimeirosElementosLinhaDeBase(listaCompleta);
        }
    }

    private LinhaDeBaseEIntervencaoObject obterMelhorLinhaDeBaseEIntervao(List<Double> listaCompleta, int tamanhoDaLista){
        var ultimoElementoDaLinhaDeBase = 0;
        var meio = tamanhoDaLista / 2;

        for(int i = 4; i < meio+1; i++){
            var calculoTauU = calcularTauUBruto(listaCompleta.subList(0, i));

            if(Math.abs(calculoTauU) < 0.3){
                ultimoElementoDaLinhaDeBase = i;
            }
        }

        if(ultimoElementoDaLinhaDeBase == 0){
            return null;
        }

        var linhaDeBase = listaCompleta.subList(0, ultimoElementoDaLinhaDeBase);
        var intervencao = listaCompleta.subList(ultimoElementoDaLinhaDeBase, tamanhoDaLista);

        return LinhaDeBaseEIntervencaoObject.builder()
                .linhaDeBase(linhaDeBase)
                .intervencao(intervencao)
                .build();
    }


    private LinhaDeBaseEIntervencaoObject obterPelaListaDividida(List<Double> listaCompleta){
        var meio = listaCompleta.size() / 2;
        var linhaDeBase = listaCompleta.subList(0, meio);
        var linhaDeIntervencao = listaCompleta.subList(meio, listaCompleta.size());

        return LinhaDeBaseEIntervencaoObject.builder()
                .linhaDeBase(linhaDeBase)
                .intervencao(linhaDeIntervencao)
                .build();
    }

    private LinhaDeBaseEIntervencaoObject obterSendoOs10PrimeirosElementosLinhaDeBase(List<Double> listaCompleta){
        var linhaDeBase = listaCompleta.subList(0, 10);
        var linhaDeIntervencao = listaCompleta.subList(10, listaCompleta.size());

        return LinhaDeBaseEIntervencaoObject.builder()
                .linhaDeBase(linhaDeBase)
                .intervencao(linhaDeIntervencao)
                .build();
    }

    private double calcularTauUBruto(List<Double> linhaBase){
        var tamanhoDaLista = linhaBase.size();

        var concordante = 0;
        var discordante = 0;

        for (int i = 0; i < tamanhoDaLista - 1; i++) {
            double valorLinhaBase1 = linhaBase.get(i);
            double valorLinhaBase2 = linhaBase.get(i+1);

            if (valorLinhaBase1 < valorLinhaBase2) {
                concordante++;
            } else if (valorLinhaBase1 > valorLinhaBase2) {
                discordante++;
            }
        }

        if((concordante + discordante) != 0){
           return (concordante - discordante) / (double) (concordante + discordante);
        }
        else{
            return 1;
        }
    }
}
