package com.brodygaudel.infractionservice.query.restclients;

import com.brodygaudel.infractionservice.common.dto.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "REGISTRATION-SERVICE")
public interface VehicleRestClient {

    @GetMapping("/query/vehicles/get/{id}")
    Vehicle getVehicleById(@PathVariable String id);
}
