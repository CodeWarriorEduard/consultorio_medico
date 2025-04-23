package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctor_id;

    // Doctor will be an user, so we remove them and let the user entity provide them.
    //    private String full_name;
    //
    //    private String email;

    private String specialty;

    private LocalDateTime avaliable_from;

    private LocalDateTime avaliable_to;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

}
