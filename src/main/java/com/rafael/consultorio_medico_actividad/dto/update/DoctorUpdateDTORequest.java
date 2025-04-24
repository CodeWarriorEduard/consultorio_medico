package com.rafael.consultorio_medico_actividad.dto.update;

import java.time.LocalDateTime;

public record DoctorUpdateDTORequest(String full_name,
                                     String email,
                                     String specialty,
                                     LocalDateTime avaliable_from,
                                     LocalDateTime avaliable_to
                                       ) {
}
