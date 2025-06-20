package com.muebles.stats.usecase.exceptions;

public class InvalidHashException extends RuntimeException {
    public InvalidHashException(String mensaje) {
        super(mensaje);
    }
}
