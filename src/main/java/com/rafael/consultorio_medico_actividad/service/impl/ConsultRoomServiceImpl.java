package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.repository.ConsultRoomRepository;
import com.rafael.consultorio_medico_actividad.service.ConsultRoomService;

import java.util.List;

public class ConsultRoomServiceImpl implements ConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository;
    private final


    @Override
    public List<ConsultRoomDTOResponse> findAllConsultRooms() {
        return List.of();
    }

    @Override
    public ConsultRoomDTOResponse getOneConsultRoom(Long id) {
        return null;
    }

    @Override
    public void deleteAConsultRoom(Long id) {

    }

    @Override
    public ConsultRoomDTOResponse registerAConsultRoom(ConsultRoomRegisterDTORequest consult_room) {
        return null;
    }

    @Override
    public ConsultRoomDTOResponse updateConsultRoom(Long id) {
        return null;
    }
}
