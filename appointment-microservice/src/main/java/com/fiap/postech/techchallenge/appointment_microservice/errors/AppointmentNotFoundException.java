package com.fiap.postech.techchallenge.appointment_microservice.errors;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException() {
        super("Appointment not found");
    }
}
