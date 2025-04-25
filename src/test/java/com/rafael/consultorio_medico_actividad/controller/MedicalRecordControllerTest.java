package com.rafael.consultorio_medico_actividad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MedicalRecordService medicalRecordService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MedicalRecordService medicalRecordService() {
            return Mockito.mock(MedicalRecordService.class);
        }
    }

    @Test
    void shouldGetAllMedicalRecords() throws Exception {

        when(medicalRecordService.findAllMedicalRecord())
                .thenReturn(List.of(new MedicalRecordDTOResponse("diagnosis 1", "notes 1")));

        mvc.perform(get("/api/v1/records/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].diagnosis").value("diagnosis 1"));
    }

    @Test
    void shouldGetMedicalRecordById() throws Exception {

        MedicalRecordDTOResponse dtoResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");

        when(medicalRecordService.getOneMedicalRecord(any())).thenReturn(dtoResponse);
        mvc.perform(get("/api/v1/records/diagnosis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosis").value("diagnosis 1"));
    }

    @Test
    void shouldGetPatientMedicalRecordByPatientId() throws Exception {
        MedicalRecordDTOResponse dtoResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");

        when(medicalRecordService.findMedicalRecordByPatientId(any())).thenReturn(List.of(dtoResponse));
        mvc.perform(get("/api/v1/records/diagnosis/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].diagnosis").value("diagnosis 1"));
    }

    @Test
    void shouldCreateAMedicalRecord() throws Exception {
        MedicalRecordDTOResponse dtoResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");
        when(medicalRecordService.registerAMedicalRecord(any())).thenReturn(dtoResponse);
        mvc.perform(put("/api/v1/records/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnosis").value("diagnosis 1"));
    }

    @Test
    void shouldDeleteAMedicalRecord() throws Exception {
        mvc.perform(delete("/api/v1/records/1"))
                .andExpect(status().isNoContent());
        verify(medicalRecordService).deleteMedicalRecord(1L);
    }
}