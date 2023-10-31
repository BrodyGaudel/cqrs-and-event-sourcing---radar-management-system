package com.brodygaudel.infractionservice.command.controller;

import com.brodygaudel.infractionservice.common.command.CreateInfractionCommand;
import com.brodygaudel.infractionservice.common.command.DeleteInfractionCommand;
import com.brodygaudel.infractionservice.common.command.UpdateInfractionCommand;
import com.brodygaudel.infractionservice.common.dto.CreateInfractionRequestDTO;
import com.brodygaudel.infractionservice.common.dto.UpdateInfractionRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/infractions")
public class InfractionCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public InfractionCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createInfraction(@RequestBody @NotNull CreateInfractionRequestDTO createInfractionRequestDTO){
        return commandGateway.send(new CreateInfractionCommand(
                UUID.randomUUID().toString(),
                createInfractionRequestDTO.date(),
                createInfractionRequestDTO.speed(),
                createInfractionRequestDTO.amount(),
                createInfractionRequestDTO.vehicleId(),
                createInfractionRequestDTO.radarId()
        ));
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<String> updateInfraction(@RequestBody @NotNull UpdateInfractionRequestDTO updateInfractionRequestDTO, @PathVariable String id){
        return commandGateway.send(new UpdateInfractionCommand(
                id,
                updateInfractionRequestDTO.date(),
                updateInfractionRequestDTO.speed(),
                updateInfractionRequestDTO.amount(),
                updateInfractionRequestDTO.vehicleId(),
                updateInfractionRequestDTO.radarId()
        ));
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> deleteInfraction(@PathVariable String id){
        return commandGateway.send(new DeleteInfractionCommand(id));
    }

    @GetMapping("/event-store/{radarId}")
    public Stream eventStore(@PathVariable String radarId){
        return eventStore.readEvents(radarId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
