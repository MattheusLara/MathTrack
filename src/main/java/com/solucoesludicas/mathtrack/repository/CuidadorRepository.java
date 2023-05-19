package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.CuidadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CuidadorRepository extends JpaRepository<CuidadorModel, UUID>{
    
}
