package com.brodygaudel.immatriculationservice.common.exceptions;

public class OwnerNotFoundException extends RuntimeException{
    public OwnerNotFoundException(String message) {
        super(message);
    }
}
