package com.brodygaudel.immatriculationservice.command.aggregate;

import com.brodygaudel.immatriculationservice.common.command.CreateOwnerCommand;
import com.brodygaudel.immatriculationservice.common.command.DeleteOwnerCommand;
import com.brodygaudel.immatriculationservice.common.command.UpdateOwnerCommand;
import com.brodygaudel.immatriculationservice.common.event.OwnerCreatedEvent;
import com.brodygaudel.immatriculationservice.common.event.OwnerDeletedEvent;
import com.brodygaudel.immatriculationservice.common.event.OwnerUpdatedEvent;
import com.brodygaudel.immatriculationservice.common.exceptions.InvalidObjectIdException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;



import java.util.Date;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@Aggregate
@Slf4j
public class OwnerAggregate {

    @AggregateIdentifier
    private String ownerId;
    private String name;
    private Date birthDate;
    private String email;

    public OwnerAggregate() {
        super();
    }

    @CommandHandler
    public OwnerAggregate(@NotNull CreateOwnerCommand command) {
        if(command.getCommandId() == null || command.getCommandId().isBlank()){
            throw new InvalidObjectIdException("Id can not be null or blank.");
        }
        AggregateLifecycle.apply( new OwnerCreatedEvent(
                command.getCommandId(),
                command.getName(),
                command.getBirthDate(),
                command.getEmail()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull OwnerCreatedEvent event){
        log.info("### Enter OwnerCreatedEvent");
        this.ownerId = event.getId();
        this.birthDate = event.getBirthDate();
        this.name = event.getName();
        this.email = event.getEmail();
    }

    @CommandHandler
    public void handle(@NotNull UpdateOwnerCommand command){
        log.info("### Enter UpdateOwnerCommand");
        if(command.getCommandId() == null || command.getCommandId().isBlank()){
            throw new InvalidObjectIdException("Id can not be null or blank.");
        }
        AggregateLifecycle.apply( new OwnerUpdatedEvent(
                command.getCommandId(),
                command.getName(),
                command.getBirthDate(),
                command.getEmail()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull OwnerUpdatedEvent event){
        log.info("### Enter OwnerUpdatedEvent");
        this.ownerId = event.getId();
        this.birthDate = event.getBirthDate();
        this.name = event.getName();
        this.email = event.getEmail();
    }

    @CommandHandler
    public void handle(@NotNull DeleteOwnerCommand command){
        log.info("### Enter DeleteOwnerCommand");
        AggregateLifecycle.apply(new OwnerDeletedEvent(
                command.getCommandId()
        ));
    }

    @EventSourcingHandler
    public void on(@NotNull OwnerDeletedEvent event){
        log.info("### Enter OwnerDeletedEvent");
        log.warn("Deleting Owner: "+ event.getId());
    }
}
