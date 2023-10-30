package com.brodygaudel.immatriculationservice.common.event;

import lombok.Getter;

@Getter
public class VehicleUpdatedEvent extends BaseEvent<String>{

    private final String numMatricule;

    private final String marque;

    private final int model;

    private final float puissanceFiscal;

    private final String ownerId;

    public VehicleUpdatedEvent(String id, String numMatricule, String marque, int model, float puissanceFiscal, String ownerId) {
        super(id);
        this.numMatricule = numMatricule;
        this.marque = marque;
        this.model = model;
        this.puissanceFiscal = puissanceFiscal;
        this.ownerId = ownerId;
    }
}
