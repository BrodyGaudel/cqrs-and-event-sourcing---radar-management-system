package com.brodygaudel.infractionservice.query.services;

import com.brodygaudel.infractionservice.common.exceptions.InfractionNotFoundException;
import com.brodygaudel.infractionservice.common.query.GetAllInfractionsQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionByIdQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionsByRadarIdQuery;
import com.brodygaudel.infractionservice.common.query.GetInfractionsByVehicleIdQuery;
import com.brodygaudel.infractionservice.query.entities.Infraction;
import com.brodygaudel.infractionservice.query.repositories.InfractionRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfractionQueryHandlerService {

    private final InfractionRepository infractionRepository;

    public InfractionQueryHandlerService(InfractionRepository infractionRepository) {
        this.infractionRepository = infractionRepository;
    }

    @QueryHandler
    public List<Infraction> handle(GetAllInfractionsQuery query){
        return infractionRepository.findAll();
    }

    @QueryHandler
    public Infraction handle(@NotNull GetInfractionByIdQuery query){
        return infractionRepository.findById(query.id()).orElseThrow(()-> new InfractionNotFoundException("Infraction not found"));
    }

    @QueryHandler
    public List<Infraction> handle(@NotNull GetInfractionsByRadarIdQuery query){
        return infractionRepository.findByRadarId(query.radarId());
    }

    @QueryHandler
    public List<Infraction> handle(@NotNull GetInfractionsByVehicleIdQuery query){
        return infractionRepository.findByVehicleId(query.vehicleId());
    }
}
