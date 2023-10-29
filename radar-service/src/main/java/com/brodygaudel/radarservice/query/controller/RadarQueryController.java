package com.brodygaudel.radarservice.query.controller;

import com.brodygaudel.radarservice.common.query.GetAllRadarsQuery;
import com.brodygaudel.radarservice.common.query.GetRadarByIdQuery;
import com.brodygaudel.radarservice.query.entities.Radar;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries/radars")
public class RadarQueryController {

    private final QueryGateway queryGateway;

    public RadarQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public List<Radar> getAllRadars(){
        return queryGateway.query(new GetAllRadarsQuery(),
                ResponseTypes.multipleInstancesOf(Radar.class)).join();
    }

    @GetMapping("/get/{radarId}")
    public Radar getRadarById(@PathVariable String radarId){
        return queryGateway.query(new GetRadarByIdQuery(radarId),
                ResponseTypes.instanceOf(Radar.class)).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
