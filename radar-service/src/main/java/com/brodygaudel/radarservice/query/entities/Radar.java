package com.brodygaudel.radarservice.query.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Radar {
    @Id
    private String id;
    private double speedLimit;
    private float longitude;
    private  float latitude;
}
