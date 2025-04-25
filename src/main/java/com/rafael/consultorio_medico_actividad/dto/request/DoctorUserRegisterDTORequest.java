package com.rafael.consultorio_medico_actividad.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record DoctorUserRegisterDTORequest(
        @NotBlank(message = "Full name cannot be blank")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        String full_name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotBlank(message = "Specialty cannot be blank")
        @Size(max = 100, message = "Specialty must not exceed 100 characters")
        String specialty,

        @NotNull(message = "Available from time cannot be null")
        LocalTime avaliable_from,

        @NotNull(message = "Available to time cannot be null")
        LocalTime avaliable_to
) {
}
