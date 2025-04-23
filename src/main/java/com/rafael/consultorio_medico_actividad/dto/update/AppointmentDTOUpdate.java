package com.rafael.consultorio_medico_actividad.dto.update;

import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentDTOUpdate(LocalDateTime start_time, LocalDateTime end_time, AppointmentStatus status) {
}
