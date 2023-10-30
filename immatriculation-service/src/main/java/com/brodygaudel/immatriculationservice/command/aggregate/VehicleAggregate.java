package com.brodygaudel.immatriculationservice.command.aggregate;

import com.brodygaudel.immatriculationservice.common.command.CreateVehicleCommand;
import com.brodygaudel.immatriculationservice.common.command.DeleteVehicleCommand;
import com.brodygaudel.immatriculationservice.common.command.UpdateVehicleCommand;
import com.brodygaudel.immatriculationservice.common.event.VehicleCreatedEvent;
import com.brodygaudel.immatriculationservice.common.event.VehicleDeletedEvent;
import com.brodygaudel.immatriculationservice.common.event.VehicleUpdatedEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jetbrains.annotations.NotNull;

@Getter
@Aggregate
@Slf4j
public class VehicleAggregate {

    @AggregateIdentifier
    private String vehicleId;

    private String numMatricule;

    private String marque;

    private int model;

    private float puissanceFiscal;

    private String ownerId;

    public VehicleAggregate() {
        super();
    }

    @CommandHandler
    public VehicleAggregate(@NotNull CreateVehicleCommand command) {
        AggregateLifecycle.apply(new VehicleCreatedEvent(
                command.getCommandId(),
                command.getNumMatricule(),
                command.getMarque(),
                command.getModel(),
                command.getPuissanceFiscal(),
                command.getOwnerId()));
    }

    @EventSourcingHandler
    public void on(@NotNull VehicleCreatedEvent event){
        log.info("### Enter VehicleCreatedEvent");
        this.vehicleId = event.getId();
        this.marque = event.getMarque();
        this.model = event.getModel();
        this.puissanceFiscal= event.getPuissanceFiscal();
        this.numMatricule = event.getNumMatricule();
        this.ownerId = event.getOwnerId();
    }

    @CommandHandler
    public void handle(@NotNull UpdateVehicleCommand command){
        log.info("### Enter UpdateVehicleCommand");
        AggregateLifecycle.apply(new VehicleCreatedEvent(
                command.getCommandId(),
                command.getNumMatricule(),
                command.getMarque(),
                command.getModel(),
                command.getPuissanceFiscal(),
                command.getOwnerId()));
    }

    @EventSourcingHandler
    public void on(@NotNull VehicleUpdatedEvent event){
        log.info("### Enter VehicleUpdatedEvent");
        this.vehicleId = event.getId();
        this.marque = event.getMarque();
        this.model = event.getModel();
        this.puissanceFiscal= event.getPuissanceFiscal();
        this.numMatricule = event.getNumMatricule();
        this.ownerId = event.getOwnerId();
    }

    @CommandHandler
    public void handle(@NotNull DeleteVehicleCommand command){
        log.info("### Enter DeleteVehicleCommand");
        AggregateLifecycle.apply(new VehicleDeletedEvent(
                command.getCommandId()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull VehicleDeletedEvent event){
        log.info("### Enter VehicleDeletedEvent");
        log.warn("Deleting Vehicle: "+ event.getId());
    }
}
