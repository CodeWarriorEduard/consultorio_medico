package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long doctor_id;

    // Doctor will be an user, so we remove them and let the user entity provide them.
    private String full_name;

    private String specialty;

    private LocalTime avaliable_from;

    private LocalTime avaliable_to;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

}
