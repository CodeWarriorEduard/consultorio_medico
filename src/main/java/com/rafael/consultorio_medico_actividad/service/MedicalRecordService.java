package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordDTOResponse> findAllDoctors();
    MedicalRecordDTOResponse getOneDoctor(Long id);
    void deleteDoctor(Long id);
    MedicalRecordDTOResponse registerADoctor(MedicalRecordRegisterDTORequest medical_record);
    MedicalRecordDTOResponse updateDoctor(Long id);
}
