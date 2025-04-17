package com.rafael.consultorio_medico_actividad.dto.request;

import com.rafael.consultorio_medico_actividad.entity.Patient;

import java.time.LocalDateTime;

public record AppointmentRegisterDTORequest(LocalDateTime start_time, LocalDateTime end_time, PatientRegisterDTORequest patient, DoctorRegisterDTORequest doctor, ConsultRoomRegisterDTORequest consult_room, MedicalRecordRegisterDTORequest medical_record ) {
}
