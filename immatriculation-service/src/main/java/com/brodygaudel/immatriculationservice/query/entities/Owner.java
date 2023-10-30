package com.brodygaudel.immatriculationservice.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Owner {
    @Id
    private String id;
    private String name;
    private Date birthDate;
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Vehicle> vehicles;
}
