package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.DoctorUserRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;

import java.util.List;

public interface DoctorService {
    List<DoctorDTOResponse> findAllDoctors();
    DoctorDTOResponse getOneDoctor(Long id);
    void deleteDoctor(Long id);
    DoctorDTOResponse registerADoctor(DoctorUserRegisterDTORequest doctor);
    DoctorDTOResponse updateDoctor(Long id, DoctorRegisterDTORequest update_data);
}
