package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.update.MedicalRecordUpdateDTO;
import com.rafael.consultorio_medico_actividad.entity.MedicalRecord;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import com.rafael.consultorio_medico_actividad.exception.notFound.AppointMentNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.MedicalRecordNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.MedicalRecordMapper;
import com.rafael.consultorio_medico_actividad.repository.AppointmentRepository;
import com.rafael.consultorio_medico_actividad.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @Mock
    MedicalRecordMapper medicalRecordMapper;

    @Mock
    AppointmentRepository appointmentRepository;

    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;

    @Test
    void findAllMedicalRecord() {

        MedicalRecord mR1 = MedicalRecord.builder()
                .notes("notes 1")
                .diagnosis("diagnosis 1")
                .build();

        MedicalRecord mR2 = MedicalRecord.builder()
                .notes("notes 2")
                .diagnosis("diagnosis 2")
                .build();

        MedicalRecordDTOResponse mr1DTOResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");
        MedicalRecordDTOResponse mr2DTOResponse = new MedicalRecordDTOResponse("diagnosis 2", "notes 2");

        when(medicalRecordRepository.findAll()).thenReturn(List.of(mR1, mR2));
        when(medicalRecordMapper.toMedicalRecordDTOResponse(mR1)).thenReturn(mr1DTOResponse);
        when(medicalRecordMapper.toMedicalRecordDTOResponse(mR2)).thenReturn(mr2DTOResponse);

        List<MedicalRecordDTOResponse> responses = medicalRecordService.findAllMedicalRecord();
        assertTrue(responses.contains(mr1DTOResponse));
        assertTrue(responses.contains(mr2DTOResponse));
        assertTrue(responses.size() == 2);
        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    void getOneMedicalRecord() {

        MedicalRecord mR1 = MedicalRecord.builder()
                .notes("notes 1")
                .diagnosis("diagnosis 1")
                .build();

        MedicalRecordDTOResponse mr1DTOResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");

        when(medicalRecordMapper.toMedicalRecordDTOResponse(mR1)).thenReturn(mr1DTOResponse);
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.of(mR1));
        MedicalRecordDTOResponse response = medicalRecordService.getOneMedicalRecord(1L);
        assertEquals(mr1DTOResponse.notes(), response.notes());
        verify(medicalRecordRepository, times(1)).findById(any());
    }

    @Test
    void whenGetOneMedicalRecordAndNotFoundThenThrowException() {
        when(medicalRecordRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.getOneMedicalRecord(1L));
        verify(medicalRecordRepository, times(1)).findById(any());
    }

    @Test
    void deleteMedicalRecord() {

        when(medicalRecordRepository.existsById(any())).thenReturn(true);
        doNothing().when(medicalRecordRepository).deleteById(any());
        medicalRecordService.deleteMedicalRecord(1L);
        verify(medicalRecordRepository, times(1)).deleteById(any());
    }

    @Test
    void whenDeleteOneMedicalRecordAndNotFoundThenThrowException() {
        when(medicalRecordRepository.existsById(any())).thenReturn(false);
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.deleteMedicalRecord(1L));
        verify(medicalRecordRepository, times(1)).existsById(any());
    }

    @Test
    void registerAMedicalRecord() {

        MedicalRecordRegisterDTORequest mr1DTORequest = new MedicalRecordRegisterDTORequest(
                "diagnosis 1",
                "notes 1",
                LocalDateTime.of(2025,1,1,1,1),
                new PatientRegisterDTORequest("pollo1","pollo@mail.com","312"),
                1L
        );

        MedicalRecord mr1 = MedicalRecord.builder()
                .diagnosis("diagnosis 1")
                .notes("notes 1")
                .created_at(LocalDateTime.of(2025,1,1,1,1))
                .patient(new Patient(1L, "pollo1","pollo@mail.com","312",null,null))
                .build();

        MedicalRecordDTOResponse mr1DTOResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 1");

        when(appointmentRepository.existsById(any())).thenReturn(true);
        when(medicalRecordRepository.save(mr1)).thenReturn(mr1);
        when(medicalRecordMapper.toMedicalRecordDTOResponse(mr1)).thenReturn(mr1DTOResponse);
        when(medicalRecordMapper.toMedicalRecord(mr1DTORequest)).thenReturn(mr1);
        MedicalRecordDTOResponse response = medicalRecordService.registerAMedicalRecord(mr1DTORequest);
        assertEquals(mr1DTOResponse.notes(), response.notes());
        verify(medicalRecordRepository, times(1)).save(mr1);
    }

    @Test
    void whenRegisterOneMedicalRecordAndNotAppointmentFoundThenThrowException() {
        when(appointmentRepository.existsById(any())).thenReturn(false);
        assertThrows(AppointMentNotFoundException.class, () -> medicalRecordService.registerAMedicalRecord(new MedicalRecordRegisterDTORequest("a","a", LocalDateTime.of(2025,1,1,1,1),new PatientRegisterDTORequest("pollo1","pollo@mail.com","312"),1L)));
        verify(appointmentRepository, times(1)).existsById(any());
    }

//    @Test
//    void updateAMedicalRecord() {
//
//        MedicalRecord mR1 = MedicalRecord.builder()
//                .notes("notes 1")
//                .diagnosis("diagnosis 1")
//                .build();
//
//        MedicalRecordUpdateDTO updateDTO = new MedicalRecordUpdateDTO("diagnosis 1", "notes 2");
//        MedicalRecordDTOResponse updateDTOResponse = new MedicalRecordDTOResponse("diagnosis 1", "notes 2");
//        when(medicalRecordRepository.existsById(any())).thenReturn(true);
//        mR1.setNotes("notes 2");
//        when(medicalRecordRepository.save(mR1)).thenReturn(mR1);
//        when(medicalRecordMapper.toMedicalRecordDTOResponse(mR1)).thenReturn(updateDTOResponse);
//        when(medicalRecordRepository.findById(any())).thenReturn(Optional.of(mR1));
//        MedicalRecordDTOResponse response = medicalRecordService.updateAMedicalRecord(updateDTO,1L);
//
//        assertEquals(updateDTOResponse.notes(), response.notes());
//        verify(medicalRecordRepository, times(1)).save(mR1);
//
//    }
//
//    @Test
//    void whenRegisterOneMedicalRecordAndNotFoundThenThrowException() {
//        MedicalRecordUpdateDTO updateDTO = new MedicalRecordUpdateDTO("diagnosis 1", "notes 2");
//        when(medicalRecordRepository.existsById(any())).thenReturn(false);
//        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.updateAMedicalRecord(updateDTO,1L));
//        verify(medicalRecordRepository, times(1)).existsById(any());
//    }
}