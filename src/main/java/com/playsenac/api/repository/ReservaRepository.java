package com.playsenac.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Integer> {

    List<ReservaEntity> findByQuadraAndStatusAndDataHoraInicioBeforeAndDataHoraFimAfter(
        QuadraEntity quadra,
        String status,
        LocalDateTime dataHoraFimDoBloqueio,
        LocalDateTime dataHoraInicioDoBloqueio
    );

}
