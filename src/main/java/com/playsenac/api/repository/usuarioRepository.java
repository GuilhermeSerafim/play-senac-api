package com.playsenac.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playsenac.api.entities.usuarioEntity;

public interface usuarioRepository extends JpaRepository<usuarioEntity, Integer>{
	Optional<usuarioEntity> findByEmail(String email);
}
