package com.fiap.postech.techchallenge.appointment_microservice.dtos;

import java.time.OffsetDateTime;

public record UpdateAppointmentDTO(
        Long id,
        OffsetDateTime startDateTime,
        OffsetDateTime endDateTime,
        Long userId,
        Long doctorId
) {
}
