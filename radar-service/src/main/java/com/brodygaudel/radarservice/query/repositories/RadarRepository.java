package com.brodygaudel.radarservice.query.repositories;

import com.brodygaudel.radarservice.query.entities.Radar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadarRepository extends JpaRepository<Radar, String> {
}
