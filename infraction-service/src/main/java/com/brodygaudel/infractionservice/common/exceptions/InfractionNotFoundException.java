package com.brodygaudel.infractionservice.common.exceptions;

public class InfractionNotFoundException extends RuntimeException{
    public InfractionNotFoundException(String message) {
        super(message);
    }
}
