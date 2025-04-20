package com.rafael.consultorio_medico_actividad.exception.notFound;

public class PatientNotFoundException extends ResourceNotFoundException{
    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException() {
        super("Patient not found");
    }
}
