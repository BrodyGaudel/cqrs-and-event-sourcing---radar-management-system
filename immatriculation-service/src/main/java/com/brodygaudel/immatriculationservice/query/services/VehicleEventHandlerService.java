package com.brodygaudel.immatriculationservice.query.services;

import com.brodygaudel.immatriculationservice.common.event.VehicleCreatedEvent;
import com.brodygaudel.immatriculationservice.common.event.VehicleDeletedEvent;
import com.brodygaudel.immatriculationservice.common.event.VehicleUpdatedEvent;
import com.brodygaudel.immatriculationservice.common.exceptions.OwnerNotFoundException;
import com.brodygaudel.immatriculationservice.common.exceptions.VehicleNotFoundException;
import com.brodygaudel.immatriculationservice.query.entities.Owner;
import com.brodygaudel.immatriculationservice.query.entities.Vehicle;
import com.brodygaudel.immatriculationservice.query.repositories.OwnerRepository;
import com.brodygaudel.immatriculationservice.query.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleEventHandlerService {
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public VehicleEventHandlerService(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    @EventHandler
    public Vehicle on(@NotNull VehicleCreatedEvent event){
        log.info("### VehicleCreatedEvent Received");
        Owner owner = ownerRepository.findById(event.getOwnerId())
                .orElseThrow(()-> new OwnerNotFoundException("Owner you want deleted do not exist"));
        Vehicle vehicle = Vehicle.builder()
                .id(event.getId())
                .marque(event.getMarque())
                .model(event.getModel())
                .numMatricule(event.getNumMatricule())
                .puissanceFiscal(event.getPuissanceFiscal())
                .owner(owner)
                .build();
        Vehicle vehicleSaved = vehicleRepository.save(vehicle);
        log.info("vehicle saved successfully with id = "+vehicleSaved.getId());
        return vehicleSaved;
    }

    @EventHandler
    public Vehicle on(@NotNull VehicleUpdatedEvent event){
        log.info("### VehicleUpdatedEvent Received");
        Owner owner = ownerRepository.findById(event.getOwnerId())
                .orElseThrow(()-> new OwnerNotFoundException("Owner you want deleted do not exist"));

        Vehicle vehicle = vehicleRepository.findById(event.getId())
                .orElseThrow(()-> new VehicleNotFoundException("Vehicle not found"));
        vehicle.setId(event.getId());
        vehicle.setMarque(event.getMarque());
        vehicle.setModel(event.getModel());
        vehicle.setNumMatricule(event.getNumMatricule());
        vehicle.setPuissanceFiscal(event.getPuissanceFiscal());
        vehicle.setOwner(owner);
        Vehicle vehicleUpdated = vehicleRepository.save(vehicle);
        log.info("vehicle updated successfully");
        return vehicleUpdated;
    }

    @EventHandler
    public void on(@NotNull VehicleDeletedEvent event){
        log.info("### VehicleUpdatedEvent Received");
        Vehicle vehicle = vehicleRepository.findById(event.getId())
                .orElseThrow(()-> new VehicleNotFoundException("Vehicle you try to deleted not found"));
        vehicleRepository.deleteById(vehicle.getId());
        log.info("vehicle deleted successfully");
    }
}
