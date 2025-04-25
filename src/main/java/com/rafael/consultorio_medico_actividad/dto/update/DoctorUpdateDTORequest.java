package com.rafael.consultorio_medico_actividad.dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record DoctorUpdateDTORequest(
        @NotBlank(message = "Full name cannot be blank")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        String full_name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message = "Specialty cannot be blank")
        @Size(max = 100, message = "Specialty must not exceed 100 characters")
        String specialty,

        @NotNull(message = "Available from time cannot be null")
        LocalTime avaliable_from,

        @NotNull(message = "Available to time cannot be null")
        LocalTime avaliable_to
) {
}
