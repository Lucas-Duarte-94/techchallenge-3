package com.fiap.postech.techchallenge.appointment_microservice.mapper;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.AppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.Appointment;

public class AppointmentMapper {
    public static AppointmentDTO toAPI(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getDoctor().getName()
        );
    }
}
