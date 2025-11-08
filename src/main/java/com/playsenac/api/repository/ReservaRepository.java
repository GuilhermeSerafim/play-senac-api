package com.playsenac.api.repository;

import com.playsenac.api.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository <ReservaEntity, Integer> {
}
