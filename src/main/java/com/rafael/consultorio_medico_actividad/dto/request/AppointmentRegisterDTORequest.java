package com.rafael.consultorio_medico_actividad.dto.request;

import com.rafael.consultorio_medico_actividad.entity.Patient;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRegisterDTORequest(
        @NotNull(message = "Start time cannot be null")
        @Future(message = "Start time must be in the future")
        LocalDateTime start_time,

        @NotNull(message = "End time cannot be null")
        @Future(message = "End time must be in the future")
        LocalDateTime end_time,

        @NotNull(message = "Patient ID cannot be null")
        Long patient_id,

        @NotNull(message = "Doctor ID cannot be null")
        Long doctor_id,

        @NotNull(message = "Consult room ID cannot be null")
        Long consult_room_id) {
}
