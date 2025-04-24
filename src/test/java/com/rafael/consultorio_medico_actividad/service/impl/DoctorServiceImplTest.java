package com.rafael.consultorio_medico_actividad.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {
//
//    @Mock
//    DoctorRepository doctorRepository;
//
//    @Mock
//    DoctorMapper doctorMapper;
//
//    @InjectMocks
//    DoctorServiceImpl doctorService;
//
//    @Test
//    void findAllDoctors() {
//        Doctor doctor1 = Doctor.builder()
//                .full_name("pollo")
//                .specialty("gallo")
//                .build();
//
//        Doctor doctor2 = Doctor.builder()
//                .full_name("pallo")
//                .specialty("gallina")
//                .build();
//
//        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse(doctor1.getFull_name(),doctor2.getSpecialty());
//        DoctorDTOResponse doctorDTOResponse2 = new DoctorDTOResponse(doctor2.getFull_name(),doctor1.getSpecialty());
//
//        when(doctorRepository.findAll()).thenReturn(List.of(doctor1,doctor2));
//        when(doctorMapper.toDoctorDtoResponse(doctor1)).thenReturn(doctorDTOResponse);
//        when(doctorMapper.toDoctorDtoResponse(doctor2)).thenReturn(doctorDTOResponse2);
//
//        List<DoctorDTOResponse> doctorDTOResponses = doctorService.findAllDoctors();
//        assertTrue(doctorDTOResponses.size() == 2);
//        assertTrue(doctorDTOResponses.contains(doctorDTOResponse));
//        assertTrue(doctorDTOResponses.contains(doctorDTOResponse2));
//        verify(doctorRepository,times(1)).findAll();
//    }
//
//    @Test
//    void getOneDoctor() {
//
//        Doctor doctor = Doctor.builder()
//                .full_name("pollo")
//                .specialty("gallo")
//                .build();
//
//        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse(doctor.getFull_name(),doctor.getSpecialty());
//
//        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));
//        when(doctorMapper.toDoctorDtoResponse(any())).thenReturn(doctorDTOResponse);
//
//        DoctorDTOResponse response = doctorService.getOneDoctor(1L);
//        assertEquals(response.full_name(),doctor.getFull_name());
//        verify(doctorRepository,times(1)).findById(1L);
//    }
//
//    @Test
//    void whenGetOneDoctorAndNotFoundThenThrowException() {
//
//        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(DoctorNotFoundException.class, () -> doctorService.getOneDoctor(1L));
//        verify(doctorRepository,times(1)).findById(1L);
//
//    }
//
//    @Test
//    void deleteDoctor() {
//
//        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));
//        doNothing().when(doctorRepository).deleteById(any());
//        doctorService.deleteDoctor(1L);
//    }
//
//    @Test
//    void whenDeleteDoctorAndNotFoundThenThrowException() {
//
//        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctor(1L));
//        verify(doctorRepository,times(1)).findById(1L);
//    }
//
//    @Test
//    void registerADoctor() {
//
//        DoctorRegisterDTORequest doctorRegisterDTORequest = new DoctorRegisterDTORequest("pollo1","pollo1@mail.com","nada xd", LocalDateTime.of(0,1,1,8,0),LocalDateTime.of(0,1,1,20,0));
//
//        Doctor doctor = Doctor.builder()
//                .full_name("pollo1")
//                .specialty("nada xd")
//                .email(doctorRegisterDTORequest.email())
//                .avaliable_from(doctorRegisterDTORequest.avaliable_from())
//                .avaliable_to(doctorRegisterDTORequest.avaliable_to())
//                .build();
//
//        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse(doctor.getFull_name(),doctor.getSpecialty());
//
//        when(doctorRepository.save(any())).thenReturn(doctor);
//        when(doctorMapper.toDoctor(doctorRegisterDTORequest)).thenReturn(doctor);
//        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(doctorDTOResponse);
//
//        DoctorDTOResponse response = doctorService.registerADoctor(doctorRegisterDTORequest);
//
//        assertEquals(response.full_name(),doctor.getFull_name());
//        verify(doctorRepository,times(1)).save(doctor);
//    }
//
//    @Test
//    void updateDoctor() {
//
//        DoctorRegisterDTORequest doctorRegisterDTORequest = new DoctorRegisterDTORequest("pollo2","pollo1@mail.com","nada xd", LocalDateTime.of(0,1,1,8,0),LocalDateTime.of(0,1,1,20,0));
//
//        Doctor doctor = Doctor.builder()
//                .full_name("pollo1")
//                .specialty("nada xd")
//                .email(doctorRegisterDTORequest.email())
//                .avaliable_from(doctorRegisterDTORequest.avaliable_from())
//                .avaliable_to(doctorRegisterDTORequest.avaliable_to())
//                .build();
//
//        DoctorDTOResponse doctorDTOResponse = new DoctorDTOResponse("pollo2",doctor.getSpecialty());
//        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));
//        doctor.setFull_name("pollo2");
//        when(doctorRepository.save(any())).thenReturn(doctor);
//        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(doctorDTOResponse);
//
//        DoctorDTOResponse response = doctorService.updateDoctor(1L, doctorRegisterDTORequest);
//
//
//        assertEquals(doctor.getFull_name(),response.full_name());
//        verify(doctorRepository,times(1)).save(doctor);
//    }
//
//    @Test
//    void whenUpdateDoctorAndNotFoundThenThrowException() {
//
//        DoctorRegisterDTORequest doctorRegisterDTORequest = new DoctorRegisterDTORequest("pollo2","pollo1@mail.com","nada xd", LocalDateTime.of(0,1,1,8,0),LocalDateTime.of(0,1,1,20,0));
//
//        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor(1L,doctorRegisterDTORequest));
//        verify(doctorRepository,times(1)).findById(1L);
//    }
}