package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.MetricasCalculadasFaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricasCalculadasFaseRepository extends JpaRepository<MetricasCalculadasFaseModel, Long>{
}
