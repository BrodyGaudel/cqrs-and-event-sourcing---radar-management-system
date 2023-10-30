package com.brodygaudel.immatriculationservice.common.command;

import lombok.Getter;

@Getter
public class CreateVehicleCommand extends BaseCommand<String>{

    private final String numMatricule;

    private final String marque;

    private final int model;

    private final float puissanceFiscal;

    private final String ownerId;

    public CreateVehicleCommand(String commandId, String numMatricule, String marque, int model, float puissanceFiscal, String ownerId) {
        super(commandId);
        this.numMatricule = numMatricule;
        this.marque = marque;
        this.model = model;
        this.puissanceFiscal = puissanceFiscal;
        this.ownerId = ownerId;
    }
}
