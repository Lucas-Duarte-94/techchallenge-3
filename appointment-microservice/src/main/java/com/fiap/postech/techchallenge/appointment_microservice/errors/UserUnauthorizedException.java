package com.fiap.postech.techchallenge.appointment_microservice.errors;

public class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException() {
        super("User is not authorized");
    }
}
