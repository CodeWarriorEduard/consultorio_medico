package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTOResponse toDoctorDtoResponse(Doctor doctor);
    Doctor toDoctor(DoctorRegisterDTORequest doctor);
}
