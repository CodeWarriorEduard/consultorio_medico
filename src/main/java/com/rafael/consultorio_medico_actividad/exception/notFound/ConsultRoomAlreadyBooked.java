package com.rafael.consultorio_medico_actividad.exception.notFound;

public class ConsultRoomAlreadyBooked extends RuntimeException {
    public ConsultRoomAlreadyBooked(String message) {
        super(message);
    }
}
