package com.rafael.consultorio_medico_actividad.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientRegisterDTORequest(
        @NotBlank(message = "Full name cannot be blank")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        String full_name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message = "Phone number cannot be blank")
        String phone) {
}
