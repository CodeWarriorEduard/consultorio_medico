package com.rafael.consultorio_medico_actividad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AppointmentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(AppointmentControllerTest.MockConfig.class)
class AppointmentControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AppointmentService appointmentService;

    @TestConfiguration
    static class MockConfig{
        public AppointmentService appointmentService(){
            return Mockito.mock(AppointmentService.class);
        }
    }

    @Test
    void shouldGetAllAppointments() throws Exception{
        when(appointmentService.findAllAppointments())
                .thenReturn(List.of(new AppointmentDTOResponse(LocalDateTime.now().plusHours(1)
                        ,LocalDateTime.now().plusHours(2)
                        ,new PatientDTOResponse(1L,"pollo1", "pollo@pollo.com", "123456"))));

        mvc.perform(get("/api/v1/appointments/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patient.full_name").value("pollo1"));
    }

    @Test
    void shoulGetAppointmentById() throws Exception{
        AppointmentDTOResponse dtoResponse = new AppointmentDTOResponse(LocalDateTime.now().plusHours(1)
                ,LocalDateTime.now().plusHours(2)
                ,new PatientDTOResponse(1L,"pollo1", "pollo@pollo.com", "123456"));

        when(appointmentService.getOneAppointment(any())).thenReturn(dtoResponse);

        mvc.perform(get("/api/v1/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient.full_name").value("pollo1"));
    }

    @Test
    void shouldCreateAppointment() throws Exception{
        AppointmentDTOResponse dtoResponse = new AppointmentDTOResponse(LocalDateTime.now().plusHours(1)
                ,LocalDateTime.now().plusHours(2)
                ,new PatientDTOResponse(1L,"pollo1", "pollo@pollo.com", "123456"));

        when(appointmentService.createAnAppointment(any())).thenReturn(dtoResponse);
        mvc.perform(put("/api/v1/appointments/new"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patient.full_name").value("pollo1"));
    }

    @Test
    void deleteAppointment() throws Exception{
        mvc.perform(delete("/api/v1/appointments/1"))
                .andExpect(status().isNoContent());
        verify(appointmentService).deleteAppointment(any());
    }
}