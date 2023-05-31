package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.CriancasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CriancasRepository extends JpaRepository<CriancasModel, UUID>{
    CriancasModel findByCpf(String cpf);
}
