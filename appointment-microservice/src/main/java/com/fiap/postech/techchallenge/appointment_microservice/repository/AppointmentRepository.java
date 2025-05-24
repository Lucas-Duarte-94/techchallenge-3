package com.fiap.postech.techchallenge.appointment_microservice.repository;

import com.fiap.postech.techchallenge.appointment_microservice.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findManyByUserEmail(String email);
}
