package com.brodygaudel.radarservice.command.controller;

import com.brodygaudel.radarservice.common.command.CreateRadarCommand;
import com.brodygaudel.radarservice.common.command.DeleteRadarCommand;
import com.brodygaudel.radarservice.common.command.UpdateRadarCommand;
import com.brodygaudel.radarservice.common.dtos.CreateRadarRequestDTO;
import com.brodygaudel.radarservice.common.dtos.UpdateRadarRequestDTO;
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
@RequestMapping(path = "/commands/radar")
public class RadarCommandController {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public RadarCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createRadar(@RequestBody @NotNull CreateRadarRequestDTO  createRadarRequestDTO){
        return commandGateway.send(new CreateRadarCommand(
                UUID.randomUUID().toString(),
                createRadarRequestDTO.speedLimit(),
                createRadarRequestDTO.longitude(),
                createRadarRequestDTO.latitude()
        ));
    }

    @PutMapping("/update/{radarId}")
    public CompletableFuture<String> updateRadar(@PathVariable String radarId, @RequestBody @NotNull UpdateRadarRequestDTO updateRadarRequestDTO){
        return commandGateway.send(new UpdateRadarCommand(
                radarId,
                updateRadarRequestDTO.speedLimit(),
                updateRadarRequestDTO.longitude(),
                updateRadarRequestDTO.latitude()
        ));
    }

    @DeleteMapping("/delete/{radarId}")
    public CompletableFuture<String> deleteRadar(@PathVariable String radarId){
        return commandGateway.send(new DeleteRadarCommand(radarId));
    }

    @GetMapping("/eventStore/{radarId}")
    public Stream eventStore(@PathVariable String radarId){
        return eventStore.readEvents(radarId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
