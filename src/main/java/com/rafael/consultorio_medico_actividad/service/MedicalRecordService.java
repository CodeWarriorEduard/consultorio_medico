package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordDTOResponse> findAllMedicalRecord();
    MedicalRecordDTOResponse getOneMedicalRecord(Long id);
    void deleteMedicalRecord(Long id);
    MedicalRecordDTOResponse registerAMedicalRecord(MedicalRecordRegisterDTORequest medical_record);
    List<MedicalRecordDTOResponse> findMedicalRecordByPatientId(Long id);
}
