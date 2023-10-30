package com.brodygaudel.immatriculationservice.query.repositories;

import com.brodygaudel.immatriculationservice.query.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
