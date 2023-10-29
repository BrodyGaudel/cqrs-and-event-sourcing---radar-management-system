package com.brodygaudel.radarservice.common.exceptions;

public class RadarNotFoundException extends RuntimeException{
    public RadarNotFoundException(String message) {
        super(message);
    }
}
