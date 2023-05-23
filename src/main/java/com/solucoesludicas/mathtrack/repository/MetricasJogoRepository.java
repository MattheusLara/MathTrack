package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricasJogoRepository extends JpaRepository<MetricasJogoModel, Long>{
        List<MetricasJogoModel> searchAllByCriancaUUIDAndJogoIDAndNumeroDaFaseOrderById(
                UUID uuid,
                Long jogoId,
                int numeroDaFase);

        @Query("SELECT DISTINCT m.numeroDaFase FROM MetricasJogoModel m WHERE m.jogoID = :jogoId AND m.criancaUUID = :criancaUUID")
        List<Integer> findDistinctNumeroDaFaseByJogoIDAndCriancaUUID(@Param("jogoId") Long jogoId, @Param("criancaUUID") UUID criancaUUID);


        List<MetricasJogoModel> searchAllByCriancaUUIDAndCondicoesAdequadasAndJogoIDAndNumeroDaFaseOrderById(
                UUID uuid,
                CondicoesAdequadasEnum condicoesAdequadas,
                Long jogoId,
                int numeroDaFase
        );
}
