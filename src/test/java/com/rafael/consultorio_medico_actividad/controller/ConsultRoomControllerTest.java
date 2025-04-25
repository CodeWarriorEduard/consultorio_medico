package com.rafael.consultorio_medico_actividad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.ConsultRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultRoomController.class)
@Import(ConsultRoomControllerTest.MockConfig.class)
class ConsultRoomControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConsultRoomService consultRoomService;

    @TestConfiguration
    static class MockConfig {

        public ConsultRoomService consultRoomService() {
            return Mockito.mock(ConsultRoomService.class);
        }
    }

    @Test
    void shouldGetAllConsultRooms() throws Exception {

        when(consultRoomService.findAllConsultRooms())
                .thenReturn(List.of(new ConsultRoomDTOResponse("consult pollo", 1)));

        mvc.perform(get("/api/v1/consult-rooms/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].full_name").value("consult pollo"));
    }

    @Test
    void shouldGetConsultRoomById() throws Exception {

        ConsultRoomDTOResponse dtoResponse = new ConsultRoomDTOResponse("consult pollo", 1);
        when(consultRoomService.getOneConsultRoom(any())).thenReturn(dtoResponse);
        mvc.perform(get("/api/v1/consult-rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("consult pollo"));
    }

    @Test
    void shouldRegisterConsultRoom() throws Exception {

        ConsultRoomDTOResponse dtoResponse = new ConsultRoomDTOResponse("consult pollo", 1);
        when(consultRoomService.registerAConsultRoom(any())).thenReturn(dtoResponse);
        mvc.perform(put("/api/v1/consult-rooms/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.full_name").value("consult pollo"));
    }

    @Test
    void shouldUpdateConsultRoom() throws Exception {

        ConsultRoomDTOResponse dtoResponse = new ConsultRoomDTOResponse("consult pollo", 1);
        when(consultRoomService.updateConsultRoom(1L, any())).thenReturn(dtoResponse);
        mvc.perform(put("/api/v1/consult-rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("consult pollo"));
    }

    @Test
    void shouldDeleteConsultRoom() throws Exception {
        mvc.perform(delete("/api/v1/consult-rooms/1"))
                .andExpect(status().isNoContent());
        verify(consultRoomService).deleteAConsultRoom(1L);
    }
}