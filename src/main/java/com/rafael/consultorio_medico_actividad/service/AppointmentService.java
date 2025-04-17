package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOResponse> findAllAppointments();
    AppointmentDTOResponse getOneAppointment(Long id);
    void deleteAppointment(Long id);
    AppointmentDTOResponse createAnAppointment(AppointmentRegisterDTORequest appointment);
    AppointmentDTOResponse updateAppointment(Long id);
}
