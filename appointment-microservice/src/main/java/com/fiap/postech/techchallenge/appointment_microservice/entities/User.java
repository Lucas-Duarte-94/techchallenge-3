package com.fiap.postech.techchallenge.appointment_microservice.entities;

import com.fiap.postech.techchallenge.appointment_microservice.enums.UserEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserEnum type;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Appointment> appointment;
}
