package com.playsenac.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.playsenac.api.entities.QuadraEntity;
import com.playsenac.api.entities.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Integer> {

	List<ReservaEntity> findByQuadraAndDataHoraInicioBeforeAndDataHoraFimAfter(
		    QuadraEntity quadra,
		    LocalDateTime dataFim,
		    LocalDateTime dataInicio
		);
	
	@Query("SELECT r FROM ReservaEntity r WHERE r.quadra.id_quadra = :idQuadra " +
	           "AND r.dataHoraInicio < :fimBloqueio " +
	           "AND r.dataHoraFim > :inicioBloqueio")
	    List<ReservaEntity> findConflitos(
	            @Param("idQuadra") Integer idQuadra,
	            @Param("inicioBloqueio") LocalDateTime inicioBloqueio,
	            @Param("fimBloqueio") LocalDateTime fimBloqueio
	    );
}
