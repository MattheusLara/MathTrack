package com.solucoesludicas.mathtrack.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "especialistas")
public class EspecialistasModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true, nullable = false, length = 512)
    private String email;
    @Column(unique = true, nullable = false, length = 512)
    private String senha;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @Column(nullable = false)
    private String nome;
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
    @Column(length = 20, nullable = false)
    private String telefone;
}
