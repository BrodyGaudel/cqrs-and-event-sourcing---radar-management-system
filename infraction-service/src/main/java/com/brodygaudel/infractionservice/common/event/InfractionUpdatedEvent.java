package com.brodygaudel.infractionservice.common.event;

import lombok.Getter;

import java.util.Date;

@Getter
public class InfractionUpdatedEvent extends BaseEvent<String> {
    private final Date date;
    private final double speed;
    private final double amount;
    private final String vehicleId;
    private final String radarId;

    public InfractionUpdatedEvent(String id, Date date, double speed, double amount, String vehicleId, String radarId) {
        super(id);
        this.date = date;
        this.speed = speed;
        this.amount = amount;
        this.vehicleId = vehicleId;
        this.radarId = radarId;
    }
}
