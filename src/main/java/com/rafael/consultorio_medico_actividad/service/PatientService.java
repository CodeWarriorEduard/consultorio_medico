package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;

import java.util.List;

public interface PatientService {

    List<PatientDTOResponse> getAllPatients();
    PatientDTOResponse getOnePatient(Long id);
    PatientDTOResponse registerAPatient(PatientRegisterDTORequest patient);
    PatientDTOResponse updateAPatient(Long id, PatientRegisterDTORequest data);
    void deleteAPatient(Long id);
}
