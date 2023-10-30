package com.brodygaudel.immatriculationservice.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vehicle {
    @Id
    private String id;
    private String numMatricule;
    private String marque;
    private int model;
    private float puissanceFiscal;

    @ManyToOne
    private Owner owner;
}
