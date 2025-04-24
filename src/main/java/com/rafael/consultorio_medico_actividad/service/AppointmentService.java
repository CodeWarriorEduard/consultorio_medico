package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
<<<<<<< HEAD
import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;
=======
import com.rafael.consultorio_medico_actividad.dto.update.AppointmentDTOUpdate;
>>>>>>> refs/remotes/origin/main

import java.util.List;


public interface AppointmentService {
    List<AppointmentDTOResponse> findAllAppointments();
    AppointmentDTOResponse getOneAppointment(Long id);
    void deleteAppointment(Long id);
    AppointmentDTOResponse createAnAppointment(AppointmentRegisterDTORequest appointment);
<<<<<<< HEAD
    AppointmentDTOResponse updateAppointment(Long id);
    AppointmentDTOResponse updateAppointmentStatus(Long id, AppointmentStatus status);
=======
    AppointmentDTOResponse updateAppointment(Long id, AppointmentDTOUpdate appointment);
>>>>>>> refs/remotes/origin/main
}
