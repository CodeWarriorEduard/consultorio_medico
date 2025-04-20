package com.rafael.consultorio_medico_actividad.service;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;

import java.util.List;

public interface ConsultRoomService {
    List<ConsultRoomDTOResponse> findAllConsultRooms();
    ConsultRoomDTOResponse getOneConsultRoom(Long id);
    void deleteAConsultRoom(Long id);
    ConsultRoomDTOResponse registerAConsultRoom(ConsultRoomRegisterDTORequest consult_room);
    ConsultRoomDTOResponse updateConsultRoom(Long id, ConsultRoomRegisterDTORequest consult_room);
}
