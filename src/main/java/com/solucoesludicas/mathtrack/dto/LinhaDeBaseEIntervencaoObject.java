package com.solucoesludicas.mathtrack.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LinhaDeBaseEIntervencaoObject {
    private List<Double> linhaDeBase;
    private List<Double> intervencao;
}
