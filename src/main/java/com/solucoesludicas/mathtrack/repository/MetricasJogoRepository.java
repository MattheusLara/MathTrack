package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricasJogoRepository extends JpaRepository<MetricasJogoModel, Long>{
        List<MetricasJogoModel> searchAllByCriancaUUIDOrderById(UUID uuid);

        List<MetricasJogoModel> searchAllByCriancaUUIDAndCondicoesAdequadasOrderById(UUID uuid, CondicoesAdequadasEnum condicoesAdequadas);
}
