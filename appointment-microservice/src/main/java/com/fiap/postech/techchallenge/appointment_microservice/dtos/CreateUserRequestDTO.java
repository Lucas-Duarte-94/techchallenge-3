package com.fiap.postech.techchallenge.appointment_microservice.dtos;

import com.fiap.postech.techchallenge.appointment_microservice.enums.UserEnum;

public record CreateUserRequestDTO(
        String name,
        String email,
        String password,
        UserEnum type
) {
}
