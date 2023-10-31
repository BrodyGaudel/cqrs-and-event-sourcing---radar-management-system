package com.brodygaudel.infractionservice.query.restclients;

import com.brodygaudel.infractionservice.common.dto.Radar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RADAR-SERVICE")
public interface RadarRestClient {

    @GetMapping("/queries/radars/get/{radarId}")
    Radar getRadarById(@PathVariable String radarId);
}
