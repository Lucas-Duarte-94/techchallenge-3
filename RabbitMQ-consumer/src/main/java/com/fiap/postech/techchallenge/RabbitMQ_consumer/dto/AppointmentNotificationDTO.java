package com.fiap.postech.techchallenge.RabbitMQ_consumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record AppointmentNotificationDTO(
        @NotNull
        Long id,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime startDateTime,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime endDateTime,

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        String doctorName,

        @NotNull
        String type
) {}
