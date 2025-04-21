package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.ConsultRoom;
import com.rafael.consultorio_medico_actividad.exception.notFound.ConsultRoomNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.ConsultRoomMapper;
import com.rafael.consultorio_medico_actividad.repository.ConsultRoomRepository;
import com.rafael.consultorio_medico_actividad.service.ConsultRoomService;

import java.util.List;

public class ConsultRoomServiceImpl implements ConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository;
    private final ConsultRoomMapper consultRoomMapper;

    public ConsultRoomServiceImpl(ConsultRoomRepository consultRoomRepository, ConsultRoomMapper consultRoomMapper) {
        this.consultRoomRepository = consultRoomRepository;
        this.consultRoomMapper = consultRoomMapper;
    }

    @Override
    public List<ConsultRoomDTOResponse> findAllConsultRooms() {
        return consultRoomRepository.findAll()
                .stream()
                .map(consultRoomMapper::toConsultRoomDTOResponse)
                .toList();
    }

    @Override
    public ConsultRoomDTOResponse getOneConsultRoom(Long id) {
        return consultRoomRepository.findById(id)
                .map(consultRoomMapper::toConsultRoomDTOResponse)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult room with id " + id + " not found"));
    }

    @Override
    public void deleteAConsultRoom(Long id) {
        if (!consultRoomRepository.existsById(id)) {
            throw new ConsultRoomNotFoundException("Consult room with id " + id + " not found");
        }
        consultRoomRepository.deleteById(id);

    }

    @Override
    public ConsultRoomDTOResponse registerAConsultRoom(ConsultRoomRegisterDTORequest consult_room) {
        return consultRoomMapper.toConsultRoomDTOResponse(consultRoomRepository.save(consultRoomMapper.toConsultRoom(consult_room)));
    }

    @Override
    public ConsultRoomDTOResponse updateConsultRoom(Long id, ConsultRoomRegisterDTORequest consult_room) {
        ConsultRoom response = consultRoomRepository.findById(id).orElseThrow(()->new ConsultRoomNotFoundException("Consult room with id " + id + " not found"));
        response.setName(consult_room.name());
        response.setFloor(consult_room.floor());
        response.setDescription(consult_room.description());
        return consultRoomMapper.toConsultRoomDTOResponse(consultRoomRepository.save(response));
    }
}
