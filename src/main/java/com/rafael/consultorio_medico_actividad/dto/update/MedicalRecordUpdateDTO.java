package com.rafael.consultorio_medico_actividad.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicalRecordUpdateDTO(
        @NotBlank(message = "Diagnosis cannot be blank")
        @Size(max = 255, message = "Diagnosis must not exceed 255 characters")
        String diagnosis,

        @NotBlank(message = "Notes cannot be blank")
        @Size(max = 1000, message = "Notes must not exceed 1000 characters")
        String notes
) {
}
