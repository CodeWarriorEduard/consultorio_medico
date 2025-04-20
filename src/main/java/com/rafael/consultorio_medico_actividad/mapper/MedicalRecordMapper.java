package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.MedicalRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    MedicalRecordDTOResponse toMedicalRecordDTOResponse(MedicalRecord medicalRecord);
    MedicalRecord toMedicalRecord(MedicalRecordRegisterDTORequest medicalRecordRegisterDTORequest);
}
