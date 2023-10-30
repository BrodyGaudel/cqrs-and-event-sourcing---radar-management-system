package com.brodygaudel.immatriculationservice.command.controller;

import com.brodygaudel.immatriculationservice.common.command.CreateOwnerCommand;
import com.brodygaudel.immatriculationservice.common.command.DeleteOwnerCommand;
import com.brodygaudel.immatriculationservice.common.command.UpdateOwnerCommand;
import com.brodygaudel.immatriculationservice.common.dto.CreateOwnerRequestDTO;
import com.brodygaudel.immatriculationservice.common.dto.UpdateOwnerRequestDTO;
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
@RequestMapping("commands/owners")
public class OwnerCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public OwnerCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createOwner(@RequestBody @NotNull CreateOwnerRequestDTO createOwnerRequestDTO){
        return commandGateway.send(
                new CreateOwnerCommand(
                        UUID.randomUUID().toString(), createOwnerRequestDTO.name(),
                        createOwnerRequestDTO.birthDate(), createOwnerRequestDTO.email()
                )
        );
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<String> updateOwner(@PathVariable String id,@RequestBody @NotNull UpdateOwnerRequestDTO updateOwnerRequestDTO){
        return  commandGateway.send(
                new UpdateOwnerCommand(
                        id, updateOwnerRequestDTO.name(), updateOwnerRequestDTO.birthDate(), updateOwnerRequestDTO.email()
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> deleteOwner(@PathVariable String id){
        return commandGateway.send(new DeleteOwnerCommand(id));
    }

    @GetMapping("/event-store/{id}")
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
