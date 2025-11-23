package com.playsenac.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.playsenac.api.entities.BloqueioEntity;
import com.playsenac.api.entities.QuadraEntity;

public interface BloqueioRepository extends JpaRepository<BloqueioEntity, Integer> {
	
	Integer countByQuadra(QuadraEntity quadra);
	
	@Query("SELECT COUNT(b) FROM BloqueioEntity b WHERE b.quadra = :quadra AND b.id_bloqueio != :idIgnorado")
    long countBloqueiosNaQuadra(@Param("quadra") QuadraEntity quadra, @Param("idIgnorado") Integer idIgnorado);
}
