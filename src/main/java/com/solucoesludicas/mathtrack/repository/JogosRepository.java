package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.JogosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogosRepository extends JpaRepository<JogosModel, Long>{
    
}
