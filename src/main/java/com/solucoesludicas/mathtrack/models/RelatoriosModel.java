package com.solucoesludicas.mathtrack.models;

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
    private Long id;
    @Column(nullable = false)
    private UUID criancaUUID;
    @Column(nullable = false)
    private UUID especialistaUUID;
    @Column(nullable = false)
    private long jogoID;
    @Lob
    @Column(nullable = false, columnDefinition = "bytea")
    private Blob conteudoDoRelatorio;
}
