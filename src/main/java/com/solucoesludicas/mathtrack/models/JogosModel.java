package com.solucoesludicas.mathtrack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "jogos")
public class JogosModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String nome;
    @Column(nullable = false)
    private String habilidadeTrabalhada;
    @Column
    private String descricaoDoJogo;


//    @Column(nullable = false)
//    private int dificuldadeDaFase; // Adicionar?
}
