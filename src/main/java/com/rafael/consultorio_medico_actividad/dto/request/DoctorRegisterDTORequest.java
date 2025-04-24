package com.rafael.consultorio_medico_actividad.dto.request;

import java.time.LocalTime;

public record DoctorRegisterDTORequest(String full_name,
                                       String email,
                                       String specialty,
                                       LocalTime avaliable_from,
                                       LocalTime avaliable_to
                                       ) {
}
