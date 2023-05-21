package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.RelatoriosModel;
import com.solucoesludicas.mathtrack.models.ResultadosTauUModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadosTauURepository extends JpaRepository<ResultadosTauUModel, Long>{
    
}
