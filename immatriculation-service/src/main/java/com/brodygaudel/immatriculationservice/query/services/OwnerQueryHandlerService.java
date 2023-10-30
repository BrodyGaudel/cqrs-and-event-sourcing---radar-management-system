package com.brodygaudel.immatriculationservice.query.services;

import com.brodygaudel.immatriculationservice.common.exceptions.OwnerNotFoundException;
import com.brodygaudel.immatriculationservice.common.query.GetAllOwnersQuery;
import com.brodygaudel.immatriculationservice.common.query.GetOwnerByIdQuery;
import com.brodygaudel.immatriculationservice.query.entities.Owner;
import com.brodygaudel.immatriculationservice.query.repositories.OwnerRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerQueryHandlerService {
    private final OwnerRepository ownerRepository;

    public OwnerQueryHandlerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @QueryHandler
    public List<Owner> handle(GetAllOwnersQuery query){
        return ownerRepository.findAll();
    }

    @QueryHandler
    public Owner handle(@NotNull GetOwnerByIdQuery query){
        return ownerRepository.findById(query.id())
                .orElseThrow(()-> new OwnerNotFoundException("Owner not found"));
    }
}
