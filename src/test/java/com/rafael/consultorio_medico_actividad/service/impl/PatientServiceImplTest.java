package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import com.rafael.consultorio_medico_actividad.exception.notFound.PatientNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.PatientMapper;
import com.rafael.consultorio_medico_actividad.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientMapper patientMapper;

    @InjectMocks
    PatientServiceImpl patientServiceImpl;

    @Test
    void getAllPatients() {
        Patient p1 = Patient.builder()
                .full_name("pollo1")
                .build();
        Patient p2 = Patient.builder()
                .full_name("pollo2")
                .build();

        PatientDTOResponse p1DTOResponse = new PatientDTOResponse(p1.getFull_name());
        PatientDTOResponse p2DTOResponse = new PatientDTOResponse(p2.getFull_name());

        when(patientRepository.findAll()).thenReturn(List.of(p1, p2));
        when(patientMapper.toPatientDtoResponse(p1)).thenReturn(p1DTOResponse);
        when(patientMapper.toPatientDtoResponse(p2)).thenReturn(p2DTOResponse);
        List<PatientDTOResponse> patientDTOResponses = patientServiceImpl.getAllPatients();
        assertTrue(patientDTOResponses.size() == 2);
        verify(patientRepository,times(1)).findAll();

    }

    @Test
    void getOnePatient() {

        Patient p1 = Patient.builder()
                .patient_id(1L)
                .full_name("pollo1")
                .build();

        PatientDTOResponse p1DTOResponse = new PatientDTOResponse(p1.getFull_name());

        when(patientRepository.findById(1L)).thenReturn(Optional.of(p1));
        when(patientMapper.toPatientDtoResponse(p1)).thenReturn(p1DTOResponse);
        PatientDTOResponse patientDTOResponse = patientServiceImpl.getOnePatient(1L);
        assertEquals(patientDTOResponse.full_name(), p1.getFull_name());
        verify(patientRepository,times(1)).findById(1L);
    }

    @Test
    void whenGetOnePatientAndNotFoundedThenItThrowsException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.getOnePatient(1L));
        verify(patientRepository,times(1)).findById(1L);
    }

    @Test
    void registerAPatient() {

        PatientRegisterDTORequest p1DTORequest = new PatientRegisterDTORequest("pollo1",null,null);

        Patient p1 = Patient.builder()
                .patient_id(1L)
                .full_name("pollo1")
                .build();


        PatientDTOResponse p1DTOResponse = new PatientDTOResponse(p1.getFull_name());

        when(patientRepository.save(p1)).thenReturn(p1);
        when(patientMapper.toPatient(p1DTORequest)).thenReturn(p1);
        when(patientMapper.toPatientDtoResponse(p1)).thenReturn(p1DTOResponse);
        PatientDTOResponse patientDTOResponse = patientServiceImpl.registerAPatient(p1DTORequest);

        assertEquals(patientDTOResponse.full_name(), p1.getFull_name());
        verify(patientRepository,times(1)).save(p1);
    }

    @Test
    void updateAPatient() {

        Patient p1 = Patient.builder()
                .patient_id(1L)
                .full_name("pollo1")
                .build();

        PatientRegisterDTORequest p1DTORequest = new PatientRegisterDTORequest("pollo2",null,null);

        PatientDTOResponse p1DTOUpdated = new PatientDTOResponse("pollo2");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(p1));
        p1.setFull_name("pollo2");
        when(patientRepository.save(p1)).thenReturn(p1);
        when(patientMapper.toPatientDtoResponse(p1)).thenReturn(p1DTOUpdated);

        PatientDTOResponse response = patientServiceImpl.updateAPatient(1L, p1DTORequest);
        assertEquals(response.full_name(), "pollo2");
        verify(patientRepository,times(1)).findById(1L);
    }

    @Test
    void whenUpdateAPatientAndNotFoundedThenItThrowsException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.updateAPatient(1L,new PatientRegisterDTORequest("pollo1",null,null)));
        verify(patientRepository,times(1)).findById(1L);
    }

    @Test
    void deleteAPatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));
        patientServiceImpl.deleteAPatient(1L);
        verify(patientRepository,times(1)).findById(1L);
    }

    @Test
    void whenDeleteAPatientAndNotFoundedThenItThrowsException() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.deleteAPatient(1L));
        verify(patientRepository,times(1)).findById(1L);
    }
}