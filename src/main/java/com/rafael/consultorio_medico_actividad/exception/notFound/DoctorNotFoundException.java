package com.rafael.consultorio_medico_actividad.exception.notFound;

public class DoctorNotFoundException extends ResourceNotFoundException{
    public DoctorNotFoundException(String message) {
        super(message);
    }

    public DoctorNotFoundException() {
        super("Doctor not found");
    }
}
