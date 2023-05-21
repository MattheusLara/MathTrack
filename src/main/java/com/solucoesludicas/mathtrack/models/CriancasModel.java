package com.solucoesludicas.mathtrack.models;

import com.solucoesludicas.mathtrack.enums.DiagnosticoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "criancas")
public class CriancasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(unique = true, length = 512)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiagnosticoEnum diagnostico;
    @Column(nullable = false)
    private LocalDateTime dataDeNascimento;
    @Column
    private String enderecoRua;
    @Column
    private String enderecoBairro;
    @Column
    private String enderecoCidade;
    @Column
    private String enderecoNumero;
    @Column
    private String enderecoComplemento;
    @Column
    private String cpf;
    @Column(length = 20)
    private String telefone;
    @Column(length = 20)
    private String telefoneResponsavel;
    @Column
    private String especialistaUUID; //Pode nao ter?
    @Column
    private String cuidadorUUID;
}
