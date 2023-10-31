package com.brodygaudel.infractionservice.command.aggregate;


import com.brodygaudel.infractionservice.common.command.CreateInfractionCommand;
import com.brodygaudel.infractionservice.common.command.DeleteInfractionCommand;
import com.brodygaudel.infractionservice.common.command.UpdateInfractionCommand;
import com.brodygaudel.infractionservice.common.event.InfractionCreatedEvent;
import com.brodygaudel.infractionservice.common.event.InfractionDeletedEvent;
import com.brodygaudel.infractionservice.common.event.InfractionUpdatedEvent;
import com.brodygaudel.infractionservice.common.exceptions.InfractionNotFoundException;
import com.brodygaudel.infractionservice.common.exceptions.InvalidObjectIdException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Getter
@Aggregate
@Slf4j
public class InfractionAggregate {

    @AggregateIdentifier
    private String id;

    private Date date;

    private double speed;

    private double amount;

    private String vehicleId;

    private String radarId;

    public InfractionAggregate() {
        super();
    }

    @CommandHandler
    public InfractionAggregate(@NotNull CreateInfractionCommand command) {
        AggregateLifecycle.apply(new InfractionCreatedEvent(
                command.getCommandId(),command.getDate(), command.getSpeed(), command.getAmount(), command.getVehicleId(),command.getRadarId()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull InfractionCreatedEvent event){
        log.info("### Enter InfractionCreatedEvent");
        this.id = event.getId();
        this.date = event.getDate();
        this.amount = event.getAmount();
        this.speed = event.getSpeed();
        this.radarId = event.getRadarId();
        this.vehicleId = event.getVehicleId();
    }

    @CommandHandler
    public void handle(@NotNull UpdateInfractionCommand command){
        log.info("### Enter UpdateInfractionCommand");
        if(command.getCommandId() == null || command.getCommandId().isBlank()) {
            throw new InvalidObjectIdException("id must not be null or blank");
        }
        AggregateLifecycle.apply(new InfractionUpdatedEvent(
                command.getCommandId(),command.getDate(), command.getSpeed(), command.getAmount(), command.getVehicleId(),command.getRadarId()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull InfractionUpdatedEvent event){
        log.info("### Enter InfractionUpdatedEvent");
        this.id = event.getId();
        this.date = event.getDate();
        this.amount = event.getAmount();
        this.speed = event.getSpeed();
        this.radarId = event.getRadarId();
        this.vehicleId = event.getVehicleId();
    }

    @CommandHandler
    public void handle(@NotNull DeleteInfractionCommand command){
        log.info("### Enter DeleteInfractionCommand");
        if(command.getCommandId() == null || command.getCommandId().isBlank()) {
            throw new InvalidObjectIdException("id must not be null or blank");
        }
        AggregateLifecycle.apply(new InfractionDeletedEvent(
                command.getCommandId())
        );
    }

    @EventSourcingHandler
    public void on(@NotNull InfractionDeletedEvent event){
        log.info("### Enter InfractionDeletedEvent");
        this.id = event.getId();
        log.warn("Deleting Infraction : "+event.getId());
    }
}
