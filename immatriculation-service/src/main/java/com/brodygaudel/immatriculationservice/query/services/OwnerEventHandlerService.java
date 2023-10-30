package com.brodygaudel.immatriculationservice.query.services;

import com.brodygaudel.immatriculationservice.common.event.OwnerCreatedEvent;
import com.brodygaudel.immatriculationservice.common.event.OwnerDeletedEvent;
import com.brodygaudel.immatriculationservice.common.event.OwnerUpdatedEvent;
import com.brodygaudel.immatriculationservice.common.exceptions.OwnerNotFoundException;
import com.brodygaudel.immatriculationservice.query.entities.Owner;
import com.brodygaudel.immatriculationservice.query.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OwnerEventHandlerService {

    private final OwnerRepository ownerRepository;

    public OwnerEventHandlerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @EventHandler
    public Owner on(@NotNull OwnerCreatedEvent event){
        log.info("### OwnerCreatedEvent Received");
        Owner owner = Owner.builder()
                .id(event.getId())
                .birthDate(event.getBirthDate())
                .name(event.getName())
                .email(event.getEmail())
                .build();
        Owner ownerSaved = ownerRepository.save(owner);
        log.info("Owner saved with id '"+ownerSaved.getId()+"'.");
        return ownerSaved;
    }

    @EventHandler
    public Owner on(@NotNull OwnerUpdatedEvent event){
        log.info("### OwnerUpdatedEvent Received");
        Owner owner = ownerRepository.findById(event.getId())
                .orElseThrow(()-> new OwnerNotFoundException("Owner with id '"+event.getId()+" 'not found"));
        owner.setId(event.getId());
        owner.setName(event.getName());
        owner.setEmail(event.getEmail());
        owner.setBirthDate(event.getBirthDate());
        owner.setEmail(event.getEmail());
        Owner ownerUpdated = ownerRepository.save(owner);
        log.info("owner updated");
        return ownerUpdated;
    }

    @EventHandler
    public void on(@NotNull OwnerDeletedEvent event){
        log.info("### OwnerDeletedEvent Received");
        Owner owner = ownerRepository.findById(event.getId())
                .orElseThrow(()-> new OwnerNotFoundException("Owner you want deleted do not exist"));
        ownerRepository.delete(owner);
    }
}
