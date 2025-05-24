package com.fiap.postech.techchallenge.appointment_microservice.controller;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.AppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.CreateAppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.dtos.UpdateAppointmentDTO;
import com.fiap.postech.techchallenge.appointment_microservice.entities.Appointment;
import com.fiap.postech.techchallenge.appointment_microservice.service.AppointmentService;
import com.fiap.postech.techchallenge.appointment_microservice.service.NotificationProducerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final NotificationProducerService notificationProducerService;

    AppointmentController(AppointmentService appointmentService, NotificationProducerService notificationProducerService) {
        this.appointmentService = appointmentService;
        this.notificationProducerService = notificationProducerService;
    }

    @QueryMapping
    public AppointmentDTO getAppointmentById(@Argument Long id, @Argument String email) {
        return this.appointmentService.getAppointmentById(id, email);
    }

    @QueryMapping
    public List<AppointmentDTO> getSelfAppointments(@Argument String email) {
        return this.appointmentService.getSelfAppointments(email);
    }

    @QueryMapping
    public List<AppointmentDTO> getAllPatientAppointmentsByEmail(@Argument String doctorEmail, @Argument String patientEmail) {
        return this.appointmentService.getAllPatientAppointmentsByEmail(doctorEmail, patientEmail);
    }

    @MutationMapping
    public AppointmentDTO createAppointment(@Argument CreateAppointmentDTO appointment, @Argument String nurserEmail) {
        return this.appointmentService.createAppointment(appointment, nurserEmail);
    }

    @MutationMapping
    public AppointmentDTO updateAppointment(@Argument String doctorEmail, @Argument UpdateAppointmentDTO appointment) {
        return this.appointmentService.updateAppointment(doctorEmail, appointment);
    }

    @MutationMapping
    public void deleteAppointment(@Argument Long id, @Argument String doctorEmail) {
        this.appointmentService.deleteAppointment(id, doctorEmail);
    }
}
