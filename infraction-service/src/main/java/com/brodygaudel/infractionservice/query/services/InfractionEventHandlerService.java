package com.brodygaudel.infractionservice.query.services;

import com.brodygaudel.infractionservice.common.dto.Radar;
import com.brodygaudel.infractionservice.common.dto.Vehicle;
import com.brodygaudel.infractionservice.common.event.InfractionCreatedEvent;
import com.brodygaudel.infractionservice.common.event.InfractionDeletedEvent;
import com.brodygaudel.infractionservice.common.event.InfractionUpdatedEvent;
import com.brodygaudel.infractionservice.common.exceptions.InfractionNotFoundException;
import com.brodygaudel.infractionservice.common.exceptions.RadarNotFoundException;
import com.brodygaudel.infractionservice.common.exceptions.VehicleNotFoundException;
import com.brodygaudel.infractionservice.query.entities.Infraction;
import com.brodygaudel.infractionservice.query.repositories.InfractionRepository;
import com.brodygaudel.infractionservice.query.restclients.RadarRestClient;
import com.brodygaudel.infractionservice.query.restclients.VehicleRestClient;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class InfractionEventHandlerService {

    private final InfractionRepository infractionRepository;
    private final RadarRestClient radarRestClient;
    private final VehicleRestClient vehicleRestClient;

    public InfractionEventHandlerService(InfractionRepository infractionRepository, RadarRestClient radarRestClient, VehicleRestClient vehicleRestClient) {
        this.infractionRepository = infractionRepository;
        this.radarRestClient = radarRestClient;
        this.vehicleRestClient = vehicleRestClient;
    }

    @EventHandler
    public Infraction on(@NotNull InfractionCreatedEvent event){
        log.info("### InfractionCreatedEvent Received");
        Radar radar = getRadarById(event.getRadarId()).orElseThrow( () -> new RadarNotFoundException("Radar not found"));
        Vehicle vehicle = getVehicleById(event.getVehicleId()).orElseThrow( () -> new VehicleNotFoundException("Vehicle not found"));
        Infraction infraction = Infraction.builder()
                .id(UUID.randomUUID().toString())
                .amount(event.getAmount())
                .date(event.getDate())
                .speed(event.getSpeed())
                .radarId(radar.id())
                .vehicleId(vehicle.id())
                .build();
        Infraction infractionSaved = infractionRepository.save(infraction);
        log.info("Infraction saved with id = "+infractionSaved.getId());
        return infractionSaved;
    }



    @EventHandler
    public Infraction on(@NotNull InfractionUpdatedEvent event){
        log.info("### InfractionUpdatedEvent Received");
        Radar radar = getRadarById(event.getRadarId()).orElseThrow( () -> new RadarNotFoundException("Radar not found"));
        Vehicle vehicle = getVehicleById(event.getVehicleId()).orElseThrow( () -> new VehicleNotFoundException("Vehicle not found"));
        Infraction infraction  = infractionRepository.findById(event.getId()).orElseThrow( () -> new InfractionNotFoundException("Infraction not found"));
        infraction.setAmount(event.getAmount());
        infraction.setDate(event.getDate());
        infraction.setRadarId(radar.id());
        infraction.setVehicleId(vehicle.id());
        infraction.setSpeed(event.getSpeed());
        Infraction infractionUpdated = infractionRepository.save(infraction);
        log.info("Infraction updated successfully with id = "+infractionUpdated.getId());
        return infractionUpdated;
    }

    @EventHandler
    public void on(@NotNull InfractionDeletedEvent event){
        log.info("### InfractionDeletedEvent Received");
        Infraction infraction  = infractionRepository.findById(event.getId()).orElseThrow( () -> new InfractionNotFoundException("Infraction not found"));
        infractionRepository.deleteById(infraction.getId());
        log.info("infraction deleted successfully");
    }

    private Optional<Radar> getRadarById(String id){
        try{
            Radar radar = radarRestClient.getRadarById(id);
            log.info("Radar found");
            return Optional.of(radar);
        }catch (Exception e){
            log.warn("Radar not found");
            return Optional.empty();
        }
    }

    private Optional<Vehicle> getVehicleById(String id){
        try{
            Vehicle vehicle = vehicleRestClient.getVehicleById(id);
            log.info("Vehicle found");
            return Optional.of(vehicle);
        }catch (Exception e){
            log.warn("Vehicle not found");
            return Optional.empty();
        }
    }
}
