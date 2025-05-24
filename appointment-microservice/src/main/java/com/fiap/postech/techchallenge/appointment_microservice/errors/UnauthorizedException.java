package com.fiap.postech.techchallenge.appointment_microservice.errors;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
