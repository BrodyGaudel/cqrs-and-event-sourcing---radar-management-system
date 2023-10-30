package com.brodygaudel.immatriculationservice.common.exceptions;

public class VehicleNotFoundException extends RuntimeException{

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
