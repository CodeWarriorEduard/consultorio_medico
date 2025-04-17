package com.rafael.consultorio_medico_actividad.dto.response;

import java.time.LocalDateTime;

public record AppointmentDTOResponse(LocalDateTime start_time, LocalDateTime end_time, PatientDTOResponse patient) {
}
