package com.brodygaudel.radarservice.command.aggregate;

import com.brodygaudel.radarservice.common.command.CreateRadarCommand;
import com.brodygaudel.radarservice.common.command.DeleteRadarCommand;
import com.brodygaudel.radarservice.common.command.UpdateRadarCommand;
import com.brodygaudel.radarservice.common.event.RadarCreatedEvent;
import com.brodygaudel.radarservice.common.event.RadarDeletedEvent;
import com.brodygaudel.radarservice.common.event.RadarUpdatedEvent;
import com.brodygaudel.radarservice.common.exceptions.InvalidObjectIdException;
import com.brodygaudel.radarservice.common.exceptions.NegativeSpeedException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

@Getter
@Slf4j
@Aggregate
public class RadarAggregate {

    @AggregateIdentifier
    private String radarId;

    private double speedLimit;

    private float longitude;

    private  float latitude;

    public RadarAggregate() {
        super();
    }

    @CommandHandler
    public RadarAggregate(@NotNull CreateRadarCommand command) {
        if(command.getSpeedLimit()<0){
            throw new NegativeSpeedException("Speed can not be negative");
        }
        AggregateLifecycle.apply(
                new RadarCreatedEvent(command.getCommandId(), command.getSpeedLimit(), command.getLongitude(), command.getLatitude())
        );
    }

    @EventSourcingHandler
    public void on(@NotNull RadarCreatedEvent event){
        log.info("### Enter RadarCreatedEvent");
        this.radarId = event.getId();
        this.speedLimit = event.getSpeedLimit();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
    }

    @CommandHandler
    public void handle(@NotNull UpdateRadarCommand command){
        log.info("### Enter UpdateRadarCommand");
        if(command.getCommandId() == null || command.getCommandId().isBlank()){
            throw new InvalidObjectIdException("ID is not valid.");
        }
        if(command.getSpeedLimit()<0){
            throw new NegativeSpeedException("Speed can not be negative");
        }
        AggregateLifecycle.apply(
                new RadarUpdatedEvent( command.getCommandId(), command.getSpeedLimit(), command.getLongitude(), command.getLatitude())
        );
    }

    @EventSourcingHandler
    public void on(@NotNull RadarUpdatedEvent event){
        log.info("### Enter RadarUpdatedEvent");
        this.radarId = event.getId();
        this.speedLimit = event.getSpeedLimit();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
    }

    @CommandHandler
    public void handle(@NotNull DeleteRadarCommand command){
        log.info("### Enter DeleteRadarCommand");
        AggregateLifecycle.apply(
                new RadarDeletedEvent(command.getCommandId())
        );
    }


}
