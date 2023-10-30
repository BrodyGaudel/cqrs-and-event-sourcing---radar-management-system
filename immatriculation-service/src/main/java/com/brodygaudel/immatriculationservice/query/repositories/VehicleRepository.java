package com.brodygaudel.immatriculationservice.query.repositories;

import com.brodygaudel.immatriculationservice.query.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
