package com.rafael.consultorio_medico_actividad.dto.request;

import com.rafael.consultorio_medico_actividad.entity.Patient;

import java.time.LocalDateTime;

public record AppointmentRegisterDTORequest(LocalDateTime start_time, LocalDateTime end_time, Long patient_id, Long doctor_id, Long consult_room_id ) {
}
