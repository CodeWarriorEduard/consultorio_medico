package com.rafael.consultorio_medico_actividad.exception.notFound;

public class MedicalRecordNotFoundException extends ResourceNotFoundException{
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }

    public MedicalRecordNotFoundException() {
        super("Medical Record not found");
    }
}
