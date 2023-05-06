package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricasJogoRepository extends JpaRepository<MetricasJogoModel, Long>{

}
