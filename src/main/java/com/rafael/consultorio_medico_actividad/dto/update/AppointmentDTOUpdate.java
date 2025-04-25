package com.rafael.consultorio_medico_actividad.dto.update;

import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDTOUpdate(
        @NotNull(message = "Start time cannot be null")
        @Future(message = "Start time must be in the present or future")
        LocalDateTime start_time,

        @NotNull(message = "End time cannot be null")
        @Future(message = "End time must be in the present or future")
        LocalDateTime end_time,

        @NotNull(message = "Appointment status cannot be null")
        AppointmentStatus status
) {
}
