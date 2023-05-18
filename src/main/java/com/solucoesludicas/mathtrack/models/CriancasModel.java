package com.solucoesludicas.mathtrack.models;

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
    @Column(nullable = false)
    private String diagnostico; //TEA TDAH DI TOD OUTROS SEM_DIAG
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
    //Mapear id cuidador e criar tabela
}
