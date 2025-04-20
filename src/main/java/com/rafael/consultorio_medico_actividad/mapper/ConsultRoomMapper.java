package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.ConsultRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {

    ConsultRoomDTOResponse toConsultRoomDTOResponse(ConsultRoom consultRoom);
    ConsultRoom toConsultRoom(ConsultRoomRegisterDTORequest consultRoomRegisterDTORequest);
}
