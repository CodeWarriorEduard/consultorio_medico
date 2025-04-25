package com.rafael.consultorio_medico_actividad.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ConsultRoomRegisterDTORequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be at most 100 characters long")
        String name,

        @NotNull(message = "Floor cannot be null")
        @Positive(message = "Floor must be a positive number")
        Integer floor,

        @Size(max = 255, message = "Description must be at most 255 characters long")
        String description) {
}
