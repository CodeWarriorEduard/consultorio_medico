package com.rafael.consultorio_medico_actividad.entity;

import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.Doc;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointment_id;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    @JoinColumn(
            name = "patient_id", referencedColumnName = "patient_id"
    )
    private Patient patient;

    @ManyToOne
    @JoinColumn(
            name = "doctor_id", referencedColumnName = "doctor_id"
    )
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(
            name = "consult_room_id", referencedColumnName = "consult_room_id"
    )
    private ConsultRoom consult_room;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord medicalRecord;



}
