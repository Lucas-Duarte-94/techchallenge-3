package com.fiap.postech.techchallenge.appointment_microservice.errors;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
