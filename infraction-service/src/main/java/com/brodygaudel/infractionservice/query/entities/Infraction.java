package com.brodygaudel.infractionservice.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Infraction {
    @Id
    private String id;
    private Date date;
    private double speed;
    private double amount;
    private String vehicleId;
    private String radarId;
}
