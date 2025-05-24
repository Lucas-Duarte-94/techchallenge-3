package com.fiap.postech.techchallenge.appointment_microservice.service;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.AppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.CreateAppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.UpdateAppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.Appointment;
import com.fiap.postech.techchallenge.appointment_microservice.errors.AppointmentNotFoundException;
import com.fiap.postech.techchallenge.appointment_microservice.errors.UnauthorizedException;
import com.fiap.postech.techchallenge.appointment_microservice.errors.UserNotFoundException;
import com.fiap.postech.techchallenge.appointment_microservice.mapper.AppointmentMapper;
import com.fiap.postech.techchallenge.appointment_microservice.mapper.AppointmentNotificationMapper;
import com.fiap.postech.techchallenge.appointment_microservice.repository.AppointmentRepository;
import com.fiap.postech.techchallenge.appointment_microservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final NotificationProducerService notificationProducerService;

    private final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository, UserService userService, NotificationProducerService notificationProducerService) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.notificationProducerService = notificationProducerService;
    }

    public AppointmentDTO createAppointment(@Argument CreateAppointmentDTO appointment, @Argument String nurserEmail) {
        var nurser = userService.getUserByEmail(nurserEmail);

        if(!nurser.type().toString().equals("NURSER")) {
            throw new UnauthorizedException("This user does not have permission to add a new appointment");
        }

        var patient = this.userRepository.findById(appointment.userId()).orElseThrow(UserNotFoundException::new);
        var doctor = this.userRepository.findById(appointment.doctorId()).orElseThrow(UserNotFoundException::new);

        var newAppointment = this.appointmentRepository.save(Appointment.builder()
                .startDateTime(appointment.startDateTime())
                .endDateTime(appointment.endDateTime())
                .user(patient)
                .doctor(doctor)
                .build());


        this.notificationProducerService.sendAppointmentNotification(AppointmentNotificationMapper.toAPI(newAppointment, "CREATE"));

        return AppointmentMapper.toAPI(newAppointment);
    }

    public AppointmentDTO updateAppointment(String doctorEmail, UpdateAppointmentDTO appointment) {
        var doctor = this.userRepository.findByEmail(doctorEmail).orElseThrow(UserNotFoundException::new);


        if(!doctor.getType().toString().equals("DOCTOR")) {
            throw new UnauthorizedException("This user does not have permission to get patient appointments");
        }

        this.appointmentRepository.findById(appointment.id()).orElseThrow(AppointmentNotFoundException::new);

        var patient = this.userRepository.findById(appointment.userId()).orElseThrow(UserNotFoundException::new);

        var updatedAppointment = this.appointmentRepository.save(Appointment.builder()
                .startDateTime(appointment.startDateTime())
                .endDateTime(appointment.endDateTime())
                .user(patient)
                .doctor(doctor)
                .id(appointment.id())
                .build());

        this.notificationProducerService.sendAppointmentNotification(AppointmentNotificationMapper.toAPI(updatedAppointment, "UPDATE"));

        return AppointmentMapper.toAPI(updatedAppointment);
    }

    public void deleteAppointment(Long id, String doctorEmail) {
        // Regra sobre deletar agendamento não especificado no Tech Challenge.

        var doctor = userService.getUserByEmail(doctorEmail);

        if(!doctor.type().toString().equals("DOCTOR")) {
            throw new UnauthorizedException("This user does not have permission to get patient appointments");
        }

        var appointment = this.appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);

        this.appointmentRepository.deleteById(id);

        this.notificationProducerService.sendAppointmentNotification(AppointmentNotificationMapper.toAPI(appointment, "DELETE"));
    }

    public AppointmentDTO getAppointmentById(Long id, String email) {
        var user = userService.getUserByEmail(email);
        var appointment = this.appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);

        boolean isOwner = Objects.equals(user.id(), appointment.getUser().getId());

        switch (user.type()) {
            case DOCTOR:
            case NURSER:
                return AppointmentMapper.toAPI(appointment);
            case PATIENT:
                if (isOwner) {
                    return AppointmentMapper.toAPI(appointment);
                } else {
                    throw new UnauthorizedException("This appointment does not belong to this user");
                }
            default:
                throw new UnauthorizedException("This user does not have permission to get patient appointments");
        }
    }

    public List<AppointmentDTO> getSelfAppointments(String email) {
        // Para o usuário verificar os próprios agendamentos.
        var appointments = this.appointmentRepository.findManyByUserEmail(email);
        return appointments.stream().map(AppointmentMapper::toAPI).collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAllPatientAppointmentsByEmail(String doctorEmail, String patientEmail) {
        var doctor = userService.getUserByEmail(doctorEmail);

        if(!doctor.type().toString().equals("DOCTOR")) {
            throw new UnauthorizedException("This user does not have permission to get patient appointments");
        }

        var appointments = this.appointmentRepository.findManyByUserEmail(patientEmail);

        return appointments.stream().map(AppointmentMapper::toAPI).collect(Collectors.toList());
    }
}
