package com.rafael.consultorio_medico_actividad.dto.request;

import java.time.LocalDateTime;

public record DoctorUserRegisterDTORequest(
        String full_name,
        String email,
        String password,
        String specialty,
        LocalDateTime avaliable_from,
        LocalDateTime avaliable_to
) {
}
