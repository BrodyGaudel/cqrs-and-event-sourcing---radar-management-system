package com.brodygaudel.infractionservice.query.controller;

import com.brodygaudel.infractionservice.common.query.GetAllInfractionsQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionByIdQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionsByRadarIdQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionsByVehicleIdQuery;
import com.brodygaudel.infractionservice.query.entities.Infraction;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/infractions")
public class InfractionQueryController {

    private final QueryGateway queryGateway;

    public InfractionQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public List<Infraction> getAllInfractions(){
        return queryGateway.query(new GetAllInfractionsQuery(),
                ResponseTypes.multipleInstancesOf(Infraction.class)).join();
    }

    @GetMapping("/list-by-vehicle/{vehicleId}")
    public List<Infraction> getInfractionsByVehicleId(@PathVariable String vehicleId){
        return queryGateway.query(new GetInfractionsByVehicleIdQuery(vehicleId),
                ResponseTypes.multipleInstancesOf(Infraction.class)).join();
    }

    @GetMapping("/list-by-radar/{radarId}")
    public List<Infraction> getInfractionsByRadarId(@PathVariable String radarId){
        return queryGateway.query(new GetInfractionsByRadarIdQuery(radarId),
                ResponseTypes.multipleInstancesOf(Infraction.class)).join();
    }

    @GetMapping("/get/{id}")
    public Infraction getInfractionById(@PathVariable String id){
        return queryGateway.query(new GetInfractionByIdQuery(id),
                ResponseTypes.instanceOf(Infraction.class)).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
