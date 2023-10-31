package com.brodygaudel.infractionservice.common.command;

import lombok.Getter;

import java.util.Date;

@Getter
public class UpdateInfractionCommand extends BaseCommand<String>  {
    private final Date date;
    private final double speed;
    private final double amount;
    private final String vehicleId;
    private final String radarId;

    public UpdateInfractionCommand(String commandId, Date date, double speed, double amount, String vehicleId, String radarId) {
        super(commandId);
        this.date = date;
        this.speed = speed;
        this.amount = amount;
        this.vehicleId = vehicleId;
        this.radarId = radarId;
    }
}
