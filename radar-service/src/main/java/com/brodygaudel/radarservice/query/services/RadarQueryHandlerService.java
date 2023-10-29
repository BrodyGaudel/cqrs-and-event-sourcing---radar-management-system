package com.brodygaudel.radarservice.query.services;

import com.brodygaudel.radarservice.common.exceptions.RadarNotFoundException;
import com.brodygaudel.radarservice.common.query.GetAllRadarsQuery;
import com.brodygaudel.radarservice.common.query.GetRadarByIdQuery;
import com.brodygaudel.radarservice.query.entities.Radar;
import com.brodygaudel.radarservice.query.repositories.RadarRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RadarQueryHandlerService {
    private final RadarRepository radarRepository;

    public RadarQueryHandlerService(RadarRepository radarRepository) {
        this.radarRepository = radarRepository;
    }

    @QueryHandler
    public Radar handle(@NotNull GetRadarByIdQuery query){
        return radarRepository.findById(query.radarId())
                .orElseThrow(()-> new RadarNotFoundException(String.format("Radar with ID [%s] not found.", query.radarId())));
    }

    @QueryHandler
    public List<Radar> handle(GetAllRadarsQuery query){
        return radarRepository.findAll();
    }
}
