package com.fiap.postech.techchallenge.appointment_microservice.dtos;

import java.time.OffsetDateTime;

public record AppointmentDTO(
        Long id,
        OffsetDateTime startDateTime,
        OffsetDateTime endDateTime,
        String doctorName
) {
}
