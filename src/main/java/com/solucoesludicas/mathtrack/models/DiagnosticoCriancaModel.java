package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.DiagnosticoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "diagnostico_crianca")
public class DiagnosticoCriancaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crianca_uuid", nullable = false)
    private CriancasModel criancaUUID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiagnosticoEnum diagnostico;

}
