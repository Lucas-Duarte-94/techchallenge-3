package com.fiap.postech.techchallenge.appointment_microservice.service;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.CreateUserRequestDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.UserDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.User;
import com.fiap.postech.techchallenge.appointment_microservice.errors.UserAlreadyExistsException;
import com.fiap.postech.techchallenge.appointment_microservice.errors.UserNotFoundException;
import com.fiap.postech.techchallenge.appointment_microservice.errors.UserUnauthorizedException;
import com.fiap.postech.techchallenge.appointment_microservice.mapper.UserMapper;
import com.fiap.postech.techchallenge.appointment_microservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(CreateUserRequestDTO request) {
        var userAlreadyExists = this.userRepository.findByEmail(request.email());

        if(userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        var user = User.builder()
                .name(request.name())
                .password(request.password())
                .email(request.email())
                .type(request.type())
                .build();

        var createdUser = this.userRepository.save(user);
        return UserMapper.toAPI(createdUser);
    }

    public UserDTO getUserById(Long id) {
        User existingUser = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return UserMapper.toAPI(existingUser);
    }

    public UserDTO getUserByEmail(String email) {
        User existingUser = this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return UserMapper.toAPI(existingUser);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public List<UserDTO> getAllUsers(String email) {
        // implementar somente para admin ter acesso a todos os usuarios
        User existingUser = this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if(!existingUser.getType().toString().equals("ADMIN")) {
            throw new UserUnauthorizedException();
        }

        var allUsers = this.userRepository.findAll();

        return allUsers.stream().map(UserMapper::toAPI).collect(Collectors.toList());
    }
}
