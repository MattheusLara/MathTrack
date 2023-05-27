package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "relatorios")
public class RelatoriosModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crianca_uuid", nullable = false)
    private UUID criancaUUID;

    @Column(name = "especialista_uuid", nullable = false)
    private UUID especialistaUUID;

    @Column(name = "jogo_id", nullable = false)
    private long jogoID;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilidade_trabalhada", nullable = false)
    private HabilidadeEnum habilidadeTrabalhada;

    @Lob
    @Column(name = "conteudo_do_relatorio", nullable = false, columnDefinition = "bytea")
    private Blob conteudoDoRelatorio;
}
