package com.fiap.postech.techchallenge.appointment_microservice.mapper;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.AppointmentNotificationDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.Appointment;

public class AppointmentNotificationMapper {
    public static AppointmentNotificationDTO toAPI(Appointment appointment, String type) {
        return new AppointmentNotificationDTO(
                appointment.getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getUser().getName(),
                appointment.getUser().getEmail(),
                appointment.getDoctor().getName(),
                type
        );
    }
}
