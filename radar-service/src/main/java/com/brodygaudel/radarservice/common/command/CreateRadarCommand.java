package com.brodygaudel.radarservice.common.command;
import lombok.Getter;

@Getter
public class CreateRadarCommand extends BaseCommand<String> {

    private final double speedLimit;

    private final float longitude;

    private final float latitude;

    public CreateRadarCommand(String commandId, double speedLimit, float longitude, float latitude) {
        super(commandId);
        this.speedLimit = speedLimit;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
