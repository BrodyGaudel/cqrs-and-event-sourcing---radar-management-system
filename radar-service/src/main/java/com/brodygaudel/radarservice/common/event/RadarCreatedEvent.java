package com.brodygaudel.radarservice.common.event;

import lombok.Getter;

@Getter
public class RadarCreatedEvent extends BaseEvent<String> {

    private final double speedLimit;

    private final float longitude;

    private final float latitude;

    public RadarCreatedEvent(String id, double speedLimit, float longitude, float latitude) {
        super(id);
        this.speedLimit = speedLimit;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
