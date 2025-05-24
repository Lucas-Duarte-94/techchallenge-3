package com.fiap.postech.techchallenge.appointment_microservice.errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
