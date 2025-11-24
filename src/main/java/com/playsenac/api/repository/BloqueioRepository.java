package com.playsenac.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.QuadraEntity;

public interface BloqueioRepository extends JpaRepository<BloqueioEntity, Integer> {
	
	Integer countByQuadra(QuadraEntity quadra);
	
	@Query("SELECT COUNT(b) FROM BloqueioEntity b WHERE b.quadra = :quadra AND b.id_bloqueio != :idIgnorado")
    long countBloqueiosNaQuadra(@Param("quadra") QuadraEntity quadra, @Param("idIgnorado") Integer idIgnorado);
	
	@Query("SELECT COUNT(b) > 0 FROM BloqueioEntity b " +
	           "WHERE b.quadra.id = :quadraId " +
	           "AND (" +
	           "  :novaReservaInicio < b.dataHoraFim " +
	           "  AND " +
	           "  :novaReservaFim > b.dataHoraInicio " +
	           ")")
	    boolean existeBloqueioNoIntervalo(@Param("quadraId") Integer integer, 
	                                      @Param("novaReservaInicio") LocalDateTime inicio, 
	                                      @Param("novaReservaFim") LocalDateTime fim);
}
