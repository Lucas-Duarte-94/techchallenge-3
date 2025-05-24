package com.fiap.postech.techchallenge.appointment_microservice.dtos;

import com.fiap.postech.techchallenge.appointment_microservice.enums.UserEnum;

public record UserDTO(
        Long id,
        String name,
        String email,
        UserEnum type
) {
}
