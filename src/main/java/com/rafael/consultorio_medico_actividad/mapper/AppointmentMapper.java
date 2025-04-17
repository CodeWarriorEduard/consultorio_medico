package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDTOResponse toAppointmentDtoResponse(Appointment appointment);

    Appointment toAppointment(AppointmentRegisterDTORequest appointment);

}
