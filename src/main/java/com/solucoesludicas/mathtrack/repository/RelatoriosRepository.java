package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.RelatoriosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatoriosRepository extends JpaRepository<RelatoriosModel, Long>{
    
}
