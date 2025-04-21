package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.ConsultRoom;
import com.rafael.consultorio_medico_actividad.exception.notFound.ConsultRoomNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.ConsultRoomMapper;
import com.rafael.consultorio_medico_actividad.repository.ConsultRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultRoomServiceImplTest {

    @Mock
    ConsultRoomRepository consultRoomRepository;

    @Mock
    ConsultRoomMapper consultRoomMapper;

    @InjectMocks
    ConsultRoomServiceImpl consultRoomService;

    @Test
    void findAllConsultRooms() {

        ConsultRoom consultRoom = ConsultRoom.builder()
                .name("consultorio 1")
                .floor(2)
                .build();

        ConsultRoom consultRoom1 = ConsultRoom.builder()
                .name("consultorio 2")
                .floor(2)
                .build();

        ConsultRoomDTOResponse consultRoomDTOResponse = new ConsultRoomDTOResponse(consultRoom.getName(), consultRoom.getFloor());

        ConsultRoomDTOResponse consultRoomDTOResponse1 = new ConsultRoomDTOResponse(consultRoom1.getName(), consultRoom1.getFloor());

        when(consultRoomRepository.findAll()).thenReturn(List.of(consultRoom, consultRoom1));
        when(consultRoomMapper.toConsultRoomDTOResponse(consultRoom)).thenReturn(consultRoomDTOResponse);
        when(consultRoomMapper.toConsultRoomDTOResponse(consultRoom1)).thenReturn(consultRoomDTOResponse1);

        List<ConsultRoomDTOResponse> response = consultRoomService.findAllConsultRooms();
        assertTrue(response.size()==2);
        assertTrue(response.contains(consultRoomDTOResponse));
        assertTrue(response.contains(consultRoomDTOResponse1));
        verify(consultRoomRepository,times(1)).findAll();

    }

    @Test
    void getOneConsultRoom() {

        ConsultRoom consultRoom = ConsultRoom.builder()
                .name("consultorio 1")
                .floor(2)
                .build();

        ConsultRoomDTOResponse consultRoomDTOResponse = new ConsultRoomDTOResponse(consultRoom.getName(), consultRoom.getFloor());

        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(consultRoom));
        when(consultRoomMapper.toConsultRoomDTOResponse(consultRoom)).thenReturn(consultRoomDTOResponse);

        ConsultRoomDTOResponse response = consultRoomService.getOneConsultRoom(1L);
        assertEquals(consultRoomDTOResponse.name(), response.name());
        verify(consultRoomRepository,times(1)).findById(any());
    }

    @Test
    void whenGetOneConsultRoomAndNotFoundThenThrowException() {
        when(consultRoomRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ConsultRoomNotFoundException.class, ()->consultRoomService.getOneConsultRoom(1L));
        verify(consultRoomRepository,times(1)).findById(1L);
    }

    @Test
    void deleteAConsultRoom() {

        when(consultRoomRepository.existsById(any())).thenReturn(true);
        doNothing().when(consultRoomRepository).deleteById(any());
        consultRoomService.deleteAConsultRoom(1L);
        verify(consultRoomRepository,times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteAConsultRoomAndNotFoundThenThrowException() {
        when(consultRoomRepository.existsById(any())).thenReturn(false);
        assertThrows(ConsultRoomNotFoundException.class, ()->consultRoomService.deleteAConsultRoom(1L));
        verify(consultRoomRepository,times(1)).existsById(1L);
    }

    @Test
    void registerAConsultRoom() {

        ConsultRoomRegisterDTORequest registerDTORequest = new ConsultRoomRegisterDTORequest("consultorio 1", 1, "consultorio 1");

        ConsultRoom consultRoom = ConsultRoom.builder()
                .name(registerDTORequest.name())
                .floor(registerDTORequest.floor())
                .description(registerDTORequest.description())
                .build();

        ConsultRoomDTOResponse consultRoomDTOResponse = new ConsultRoomDTOResponse(consultRoom.getName(), consultRoom.getFloor());

        when(consultRoomRepository.save(any())).thenReturn(consultRoom);
        when(consultRoomMapper.toConsultRoom(registerDTORequest)).thenReturn(consultRoom);
        when(consultRoomMapper.toConsultRoomDTOResponse(consultRoom)).thenReturn(consultRoomDTOResponse);

        ConsultRoomDTOResponse response = consultRoomService.registerAConsultRoom(registerDTORequest);
        assertEquals(consultRoomDTOResponse.name(), response.name());
        verify(consultRoomRepository,times(1)).save(consultRoom);
    }

    @Test
    void updateConsultRoom() {

        ConsultRoomRegisterDTORequest registerDTORequest = new ConsultRoomRegisterDTORequest("consultorio 2", 1, "consultorio 1");

        ConsultRoom consultRoom = ConsultRoom.builder()
                .name("consultorio 1")
                .floor(1)
                .description("consultorio 1")
                .build();

        ConsultRoomDTOResponse consultRoomDTOResponse = new ConsultRoomDTOResponse("consultorio 2", consultRoom.getFloor());

        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(consultRoom));
        consultRoom.setName("consultorio 2");
        when(consultRoomRepository.save(any())).thenReturn(consultRoom);
        when(consultRoomMapper.toConsultRoomDTOResponse(consultRoom)).thenReturn(consultRoomDTOResponse);

        ConsultRoomDTOResponse response = consultRoomService.updateConsultRoom(1L, registerDTORequest);

        assertEquals(consultRoomDTOResponse.name(), response.name());
        verify(consultRoomRepository,times(1)).save(consultRoom);

    }

    @Test
    void whenUpdateConsultRoomAndNotFoundThenThrowException() {

        ConsultRoomRegisterDTORequest registerDTORequest = new ConsultRoomRegisterDTORequest("consultorio 2", 1, "consultorio 1");
        when(consultRoomRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ConsultRoomNotFoundException.class, ()->consultRoomService.updateConsultRoom(1L,registerDTORequest));
        verify(consultRoomRepository,times(1)).findById(1L);
    }


}