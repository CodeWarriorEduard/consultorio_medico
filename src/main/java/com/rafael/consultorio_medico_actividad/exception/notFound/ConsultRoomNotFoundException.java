package com.rafael.consultorio_medico_actividad.exception.notFound;

public class ConsultRoomNotFoundException extends ResourceNotFoundException{
    public ConsultRoomNotFoundException(String message) {
        super(message);
    }

    public ConsultRoomNotFoundException() {
        super("ConsultRoom not found");
    }
}
