package com.playsenac.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playsenac.api.security.UsuarioSistema;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Integer>{
	Optional<UsuarioSistema> findByEmail(String email);
}
