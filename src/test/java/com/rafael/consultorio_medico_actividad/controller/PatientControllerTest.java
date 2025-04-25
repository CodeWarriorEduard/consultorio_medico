package com.rafael.consultorio_medico_actividad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.PatientService;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@Import(PatientControllerTest.MockConfig.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientService patientService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        when(patientService.getAllPatients()).thenReturn(List.of(new PatientDTOResponse("pollo1")));

        mvc.perform(get("/api/v1/patients/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].full_name").value("pollo1"));
    }

    @Test
    void shouldGetPatientById() throws Exception {
        PatientDTOResponse dtoResponse = new PatientDTOResponse("pollo1");
        when(patientService.getOnePatient(any())).thenReturn(dtoResponse);
        mvc.perform(get("/api/v1/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("pollo1"));

    }

    @Test
    void shouldCreateAPatient() throws Exception {
        PatientDTOResponse dtoResponse = new PatientDTOResponse("pollo1");

        when(patientService.registerAPatient(any())).thenReturn(dtoResponse);

        mvc.perform(post("/api/v1/patients/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.full_name").value("pollo1"));
    }

    @Test
    void shouldUpdateAPatient() throws Exception {

        PatientDTOResponse dtoResponse = new PatientDTOResponse("pollo1");

        when(patientService.updateAPatient(1L, any())).thenReturn(dtoResponse);

        mvc.perform(put("/api/v1/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("pollo1"));
    }

    @Test
    void shouldDeletePatient() throws Exception {
        mvc.perform(delete("/api/v1/patients/1"))
                .andExpect(status().isNoContent());
        verify(patientService).deleteAPatient(1L);
    }

}