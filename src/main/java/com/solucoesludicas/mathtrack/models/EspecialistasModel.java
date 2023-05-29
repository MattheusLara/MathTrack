package com.solucoesludicas.mathtrack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "especialistas")
public class EspecialistasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "email", unique = true, nullable = false, length = 512)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

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

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;
}
