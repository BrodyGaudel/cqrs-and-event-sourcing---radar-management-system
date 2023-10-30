package com.brodygaudel.immatriculationservice.common.exceptions;

public class InvalidObjectIdException extends RuntimeException{
    public InvalidObjectIdException(String message) {
        super(message);
    }
}
