package com.rafael.consultorio_medico_actividad.exception;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
