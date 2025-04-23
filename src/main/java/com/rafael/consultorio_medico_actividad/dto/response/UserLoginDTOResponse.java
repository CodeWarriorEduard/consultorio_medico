package com.rafael.consultorio_medico_actividad.dto.response;

import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;

public record UserLoginDTOResponse(String user, String password, Long role_id, String token) {
}
