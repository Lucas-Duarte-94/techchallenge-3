package com.fiap.postech.techchallenge.appointment_microservice.mapper;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.UserDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.User;
import com.fiap.postech.techchallenge.appointment_microservice.enums.UserEnum;

public class UserMapper {
    public static UserDTO toAPI(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                UserEnum.valueOf(user.getType().toString())
        );
    }
}
