package com.rafael.consultorio_medico_actividad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.DoctorService;
import com.rafael.consultorio_medico_actividad.service.interfaces.PatientService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@WebMvcTest(DoctorController.class)
@Import(DoctorControllerTest.MockConfig.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private DoctorService doctorService;

    @TestConfiguration
    static class MockConfig {
        public DoctorService doctorService() {
            return Mockito.mock(DoctorService.class);
        }
    }

    @Test
    void shouldGetAllDoctors() throws Exception {
        when(doctorService.findAllDoctors())
                .thenReturn(List.of(new DoctorDTOResponse("pollo1", "gallo1")));
        mvc.perform(get("/api/v1/doctors/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].full_name").value("pollo1"));
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse("pollo1", "gallo1");

        when(doctorService.getOneDoctor(any())).thenReturn(doctorDTOResponse);
        mvc.perform(get("/api/v1/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("pollo1"));
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse("pollo1", "gallo1");

        when(doctorService.registerADoctor(any())).thenReturn(doctorDTOResponse);
        mvc.perform(put("/api/v1/doctors/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(doctorDTOResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.full_name").value("pollo1"));
    }

    @Test
    void shouldUpdateDoctor() throws Exception {

        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse("pollo1", "gallo1");

        when(doctorService.updateDoctor(1L, any())).thenReturn(doctorDTOResponse);
        mvc.perform(put("/api/v1/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(doctorDTOResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.full_name").value("pollo1"));

    }

    @Test
    void shouldDeleteADoctor() throws Exception {
        mvc.perform(delete("/api/v1/doctors/1"))
                .andExpect(status().isNoContent());
        verify(doctorService).deleteDoctor(1L);
    }

}