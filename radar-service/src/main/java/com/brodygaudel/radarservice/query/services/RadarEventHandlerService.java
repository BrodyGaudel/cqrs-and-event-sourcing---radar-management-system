package com.brodygaudel.radarservice.query.services;

import com.brodygaudel.radarservice.common.event.RadarCreatedEvent;
import com.brodygaudel.radarservice.common.event.RadarDeletedEvent;
import com.brodygaudel.radarservice.common.event.RadarUpdatedEvent;
import com.brodygaudel.radarservice.common.exceptions.RadarNotFoundException;
import com.brodygaudel.radarservice.query.entities.Radar;
import com.brodygaudel.radarservice.query.repositories.RadarRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RadarEventHandlerService {

    private final RadarRepository radarRepository;

    public RadarEventHandlerService(RadarRepository radarRepository) {
        this.radarRepository = radarRepository;
    }

    @EventHandler
    public Radar on(@NotNull RadarCreatedEvent event){
        log.info("### RadarCreatedEvent Received");
        Radar radarSaved = radarRepository.save( new Radar(
                event.getId(), event.getSpeedLimit(),
                event.getLongitude(), event.getLatitude()
        ));
        log.info("### Radar saved with id '"+radarSaved.getId()+"'.");
        return radarSaved;
    }

    @EventHandler
    public Radar on(@NotNull RadarUpdatedEvent event){
        log.info("### RadarUpdatedEvent Received");
        Radar radar = radarRepository.findById(event.getId())
                .orElseThrow( () -> new RadarNotFoundException(String.format("Radar with ID [%s] Not Found !", event.getId())));
        radar.setSpeedLimit(event.getSpeedLimit());
        radar.setLongitude(event.getLongitude());
        radar.setLatitude(event.getLatitude());
        return radarRepository.save(radar);
    }

    @EventHandler
    public void on(@NotNull RadarDeletedEvent event) {
        log.info("### RadarDeletedEvent Received");
        Radar radar = radarRepository.findById(event.getId())
                .orElseThrow( () -> new RadarNotFoundException(String.format("Radar with ID [%s] Not Found !", event.getId())));
        radarRepository.deleteById(radar.getId());
    }
}
