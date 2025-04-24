package com.rafael.consultorio_medico_actividad.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
public enum RolesEnum {

    DOCTOR("ROLE_DOCTOR"), ADMIN("ROLE_ADMIN");

    private final String role;

    private RolesEnum(String role) {
        this.role = role;
    }

}
