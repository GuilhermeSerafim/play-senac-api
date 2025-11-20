package com.playsenac.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playsenac.api.entities.DisponibilidadeEntity;

public interface DisponibilidadeRepository extends JpaRepository<DisponibilidadeEntity, Integer>{
	List<DisponibilidadeEntity> findByQuadraId(int quadraId);
}
