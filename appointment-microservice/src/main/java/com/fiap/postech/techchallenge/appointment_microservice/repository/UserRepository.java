package com.fiap.postech.techchallenge.appointment_microservice.repository;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.UserDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
