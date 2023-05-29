package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilidade_trabalhada", nullable = false)
    private HabilidadeEnum habilidadeTrabalhada;

    @Column(name = "descricao_do_jogo", length = 512)
    private String descricaoDoJogo;
}
