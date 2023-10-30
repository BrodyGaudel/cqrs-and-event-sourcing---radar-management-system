package com.brodygaudel.immatriculationservice.command.controller;

import com.brodygaudel.immatriculationservice.common.command.CreateVehicleCommand;
import com.brodygaudel.immatriculationservice.common.command.DeleteVehicleCommand;
import com.brodygaudel.immatriculationservice.common.command.UpdateVehicleCommand;
import com.brodygaudel.immatriculationservice.common.dto.CreateVehicleRequestDTO;
import com.brodygaudel.immatriculationservice.common.dto.UpdateVehicleRequestDTO;
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
@RequestMapping("/commands/vehicles")
public class VehicleCommandController {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public VehicleCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createVehicle(@RequestBody @NotNull CreateVehicleRequestDTO createVehicleRequestDTO){
        return commandGateway.send(new CreateVehicleCommand(
                UUID.randomUUID().toString(),
                createVehicleRequestDTO.numMatricule(),
                createVehicleRequestDTO.marque(),
                createVehicleRequestDTO.model(),
                createVehicleRequestDTO.puissanceFiscal(),
                createVehicleRequestDTO.ownerId()
        ));
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<String> updateVehicle(@RequestBody @NotNull UpdateVehicleRequestDTO updateOwnerRequestDTO, @PathVariable String id){
        return commandGateway.send(new UpdateVehicleCommand(
                id,
                updateOwnerRequestDTO.numMatricule(),
                updateOwnerRequestDTO.marque(),
                updateOwnerRequestDTO.model(),
                updateOwnerRequestDTO.puissanceFiscal(),
                updateOwnerRequestDTO.ownerId()
        ));
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> deleteVehicle(@PathVariable String id){
        return commandGateway.send(new DeleteVehicleCommand(id));
    }

    @GetMapping("/eventStore/{id}")
    public Stream eventStore(@PathVariable String id){
        return eventStore.readEvents(id).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
