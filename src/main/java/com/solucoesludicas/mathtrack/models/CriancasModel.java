package com.solucoesludicas.mathtrack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "criancas")
public class CriancasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "email", unique = true, length = 512)
    private String email;

    @Column(name = "nome", unique = true, nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "criancaUUID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiagnosticoCriancaModel> diagnosticos = new ArrayList<>();

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDateTime dataDeNascimento;

    @Column(name = "endereco_rua")
    private String enderecoRua;

    @Column(name = "endereco_bairro", length = 100)
    private String enderecoBairro;

    @Column(name = "endereco_cidade", length = 100)
    private String enderecoCidade;

    @Column(name = "endereco_numero", length = 20)
    private String enderecoNumero;

    @Column(name = "endereco_complemento")
    private String enderecoComplemento;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "telefone_responsavel", length = 20)
    private String telefoneResponsavel;

    @Column(name = "especialista_uuid")
    private String especialistaUUID;

    @Column(name = "cuidador_uuid")
    private String cuidadorUUID;
}
