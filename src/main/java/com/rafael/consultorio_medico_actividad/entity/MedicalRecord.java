package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long medical_record_id;

    private String diagnosis;

    private String notes;

    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(
            name = "patient_id", referencedColumnName = "patient_id"
    )
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointment_id")
    private Appointment appointment;

}
