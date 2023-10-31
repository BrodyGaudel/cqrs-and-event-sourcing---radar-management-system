package com.brodygaudel.infractionservice.common.exceptions;

public class RadarNotFoundException extends RuntimeException{
    public RadarNotFoundException(String message) {
        super(message);
    }
}
