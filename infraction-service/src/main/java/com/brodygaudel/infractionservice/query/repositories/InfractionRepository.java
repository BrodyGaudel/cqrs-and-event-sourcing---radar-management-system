package com.brodygaudel.infractionservice.query.repositories;

import com.brodygaudel.infractionservice.query.entities.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfractionRepository extends JpaRepository<Infraction, String> {

    @Query("select i from Infraction i where i.radarId =?1")
    List<Infraction> findByRadarId(String radarId);

    @Query("select i from Infraction i where i.vehicleId =?1")
    List<Infraction> findByVehicleId(String vehicleId);
}
