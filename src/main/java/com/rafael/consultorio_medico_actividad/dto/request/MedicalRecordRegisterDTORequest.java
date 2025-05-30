package com.rafael.consultorio_medico_actividad.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record MedicalRecordRegisterDTORequest(
        @NotBlank(message = "Diagnosis cannot be blank")
        @Size(max = 255, message = "Diagnosis must not exceed 255 characters")
        String diagnosis,

        @NotBlank(message = "Notes cannot be blank")
        @Size(max = 1000, message = "Notes must not exceed 1000 characters")
        String notes,

        @NotNull(message = "Creation date cannot be null")
        @Future(message = "Creation date must be in the present or future")
        LocalDateTime created_at,

        @NotNull(message = "Patient cannot be null")
        @Valid
        Long patient_id,

        @NotNull(message = "Appointment ID cannot be null")
        Long appointment_id) {
}
