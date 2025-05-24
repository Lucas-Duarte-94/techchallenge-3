package com.fiap.postech.techchallenge.appointment_microservice.controller;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.CreateUserRequestDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.UserDTO;
import com.fiap.postech.techchallenge.appointment_microservice.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public UserDTO createUser(@Argument CreateUserRequestDTO requestDTO) {
        return this.userService.createUser(requestDTO);
    }

    @QueryMapping
    public UserDTO getUserByEmail(@Argument String email) {
        return this.userService.getUserByEmail(email);
    }

    @QueryMapping
    public UserDTO getUserById(@Argument Long id) {
        return this.userService.getUserById(id);
    }

    @QueryMapping
    public List<UserDTO> getAllUsers(@Argument String email) {
        return this.userService.getAllUsers(email);
    }

    @MutationMapping
    public void deleteUserById(@Argument Long id) {
        this.userService.deleteUser(id);
    }
}
