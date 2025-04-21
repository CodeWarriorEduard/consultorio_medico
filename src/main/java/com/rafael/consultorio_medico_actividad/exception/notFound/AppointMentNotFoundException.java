package com.rafael.consultorio_medico_actividad.exception.notFound;

public class AppointMentNotFoundException extends ResourceNotFoundException{
    public AppointMentNotFoundException(String message) {
        super(message);
    }

    public AppointMentNotFoundException() {
        super("Appointment not found");
    }
}
