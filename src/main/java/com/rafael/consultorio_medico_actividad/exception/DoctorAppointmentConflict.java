package com.rafael.consultorio_medico_actividad.exception;

public class DoctorAppointmentConflict extends RuntimeException {
    public DoctorAppointmentConflict(String message) {
        super(message);
    }
}
