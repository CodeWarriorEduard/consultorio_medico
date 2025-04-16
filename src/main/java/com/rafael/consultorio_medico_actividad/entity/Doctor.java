package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctor_id;

    private String full_name;

    private String email;

    private String speciality;

    private LocalDateTime avaliable_from;

    private LocalDateTime avaliable_to;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

}
