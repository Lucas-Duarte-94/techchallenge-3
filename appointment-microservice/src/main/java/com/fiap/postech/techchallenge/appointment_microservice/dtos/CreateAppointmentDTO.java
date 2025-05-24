package com.fiap.postech.techchallenge.appointment_microservice.dtos;

import java.time.OffsetDateTime;

public record CreateAppointmentDTO(
        OffsetDateTime startDateTime,
        OffsetDateTime endDateTime,
        Long userId,
        Long doctorId
) {
}
