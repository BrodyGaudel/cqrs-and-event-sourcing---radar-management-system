package com.brodygaudel.immatriculationservice.query.services;

import com.brodygaudel.immatriculationservice.common.exceptions.OwnerNotFoundException;
import com.brodygaudel.immatriculationservice.common.query.GetAllVehiclesQuery;
import com.brodygaudel.immatriculationservice.common.query.GetVehicleByIdQuery;
import com.brodygaudel.immatriculationservice.query.entities.Vehicle;
import com.brodygaudel.immatriculationservice.query.repositories.VehicleRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleQueryHandlerService {

    private final VehicleRepository vehicleRepository;

    public VehicleQueryHandlerService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @QueryHandler
    public List<Vehicle> handle(GetAllVehiclesQuery query){
        return vehicleRepository.findAll();
    }

    @QueryHandler
    public Vehicle handle(@NotNull GetVehicleByIdQuery query){
        return vehicleRepository.findById(query.id())
                .orElseThrow(()-> new OwnerNotFoundException("Owner not found"));
    }
}
