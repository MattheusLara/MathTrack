package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.EspecialistasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EspecialistasRepository extends JpaRepository<EspecialistasModel, UUID>{
    
}
