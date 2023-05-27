package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.MetricasCalculadasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricasCalculadasFaseRepository extends JpaRepository<MetricasCalculadasModel, Long>{
}
