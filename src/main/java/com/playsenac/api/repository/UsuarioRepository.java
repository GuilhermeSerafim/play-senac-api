package com.playsenac.api.repository;

import com.playsenac.api.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <UsuarioEntity,Integer> {
    Optional<UsuarioEntity> findByEmail(String email);
    
}
