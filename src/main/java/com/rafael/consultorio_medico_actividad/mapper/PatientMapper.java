package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTOResponse toPatientDtoResponse(Patient patient);
    Patient toPatient(PatientRegisterDTORequest patient);
}
