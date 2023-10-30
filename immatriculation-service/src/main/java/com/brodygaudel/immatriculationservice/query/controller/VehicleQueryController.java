package com.brodygaudel.immatriculationservice.query.controller;

import com.brodygaudel.immatriculationservice.common.query.GetAllVehiclesQuery;
import com.brodygaudel.immatriculationservice.common.query.GetVehicleByIdQuery;
import com.brodygaudel.immatriculationservice.query.entities.Vehicle;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/vehicles")
public class VehicleQueryController {
    private final QueryGateway queryGateway;

    public VehicleQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles(){
        return queryGateway.query(new GetAllVehiclesQuery(),
                ResponseTypes.multipleInstancesOf(Vehicle.class)).join();
    }

    @GetMapping("/get/{id}")
    public Vehicle getVehicleById(@PathVariable String id){
        return queryGateway.query(new GetVehicleByIdQuery(id),
                ResponseTypes.instanceOf(Vehicle.class)).join();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
