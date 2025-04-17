package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.DoctorRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOResponse> findAllDoctors();
    AppointmentDTOResponse getOneDoctor(Long id);
    void deleteDoctor(Long id);
    AppointmentDTOResponse registerADoctor(AppointmentRegisterDTORequest appointment);
    AppointmentDTOResponse updateDoctor(Long id);
}
