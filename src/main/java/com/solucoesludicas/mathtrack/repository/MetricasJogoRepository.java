package com.solucoesludicas.mathtrack.repository;

import com.solucoesludicas.mathtrack.enums.CondicoesAdequadasEnum;
import com.solucoesludicas.mathtrack.enums.HabilidadeEnum;
import com.solucoesludicas.mathtrack.models.MetricasJogoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricasJogoRepository extends JpaRepository<MetricasJogoModel, Long>{
        List<MetricasJogoModel> searchAllByCriancaUUIDAndHabilidadeTrabalhadaAndDificuldadeDaFaseOrderById(
                UUID uuid,
                HabilidadeEnum habilidadetrabalhada,
                Integer dificuldade);

        @Query("SELECT DISTINCT m.dificuldadeDaFase FROM MetricasJogoModel m WHERE m.criancaUUID = :criancaUUID AND m.habilidadeTrabalhada = :habilidadeTrabalhada")
        List<Integer> findDistinctDificuldadeDeFasesByCriancaUUIDAndHabilidadeTrabalhada(@Param("criancaUUID") UUID criancaUUID, @Param("habilidadeTrabalhada") HabilidadeEnum habilidadeTrabalhada);

        @Query("SELECT DISTINCT m.habilidadeTrabalhada FROM MetricasJogoModel m WHERE m.criancaUUID = :criancaUUID")
        List<HabilidadeEnum> findDistinctHabilidadeTrabalhadaByCriancaUUID(@Param("criancaUUID") UUID criancaUUID);

        List<MetricasJogoModel> searchAllByCriancaUUIDAndCondicoesAdequadasAndHabilidadeTrabalhadaAndDificuldadeDaFaseOrderById(
                UUID uuid,
                CondicoesAdequadasEnum condicoesAdequadas,
                HabilidadeEnum habilidadetrabalhada,
                Integer dificuldade
        );
}
