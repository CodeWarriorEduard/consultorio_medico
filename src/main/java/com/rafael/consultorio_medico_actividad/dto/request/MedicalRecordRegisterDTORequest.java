package com.rafael.consultorio_medico_actividad.dto.request;

import java.time.LocalDateTime;

public record MedicalRecordRegisterDTORequest(String diagnosis, String notes, LocalDateTime created_at, PatientRegisterDTORequest patient, Long appointment_id) {
}
