package com.playsenac.api.repository;

import com.playsenac.api.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <UsuarioEntity,Integer> {
}
