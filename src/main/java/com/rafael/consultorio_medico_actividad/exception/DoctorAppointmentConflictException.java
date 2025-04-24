package com.rafael.consultorio_medico_actividad.exception;

public class DoctorAppointmentConflictException extends RuntimeException {
    public DoctorAppointmentConflictException(String message) {
        super(message);
    }
}
