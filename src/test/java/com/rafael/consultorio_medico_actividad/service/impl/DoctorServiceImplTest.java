package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorUserRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.update.DoctorUpdateDTORequest;
import com.rafael.consultorio_medico_actividad.entity.Doctor;
import com.rafael.consultorio_medico_actividad.entity.Roles;
import com.rafael.consultorio_medico_actividad.entity.User;
import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import com.rafael.consultorio_medico_actividad.exception.UserAlreadyRegistered;
import com.rafael.consultorio_medico_actividad.exception.notFound.DoctorNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.RoleNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.DoctorMapper;
import com.rafael.consultorio_medico_actividad.repository.DoctorRepository;
import com.rafael.consultorio_medico_actividad.repository.RolesRepository;
import com.rafael.consultorio_medico_actividad.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    DoctorMapper doctorMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    RolesRepository rolesRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    DoctorServiceImpl doctorService;

    private Doctor doctor1;
    private Doctor doctor2;
    private DoctorDTOResponse doctorDTOResponse1;
    private DoctorDTOResponse doctorDTOResponse2;
    private DoctorUserRegisterDTORequest doctorUserRegisterDTORequest;
    private DoctorUpdateDTORequest doctorUpdateDTORequest;
    private User user;
    private Roles role;

    @BeforeEach
    void setUp() {
        doctor1 = Doctor.builder()
                .doctor_id(1L)
                .full_name("pollo")
                .specialty("gallo")
                .build();

        doctor2 = Doctor.builder()
                .doctor_id(2L)
                .full_name("pallo")
                .specialty("gallina")
                .build();

        doctorDTOResponse1 = new DoctorDTOResponse(doctor1.getFull_name(), doctor1.getSpecialty());
        doctorDTOResponse2 = new DoctorDTOResponse(doctor2.getFull_name(), doctor2.getSpecialty());

        doctorUserRegisterDTORequest = new DoctorUserRegisterDTORequest(
                "pollo1", "pollo1@mail.com", "password",
                "specialty", LocalTime.of(8, 0), LocalTime.of(17, 0)
        );

        doctorUpdateDTORequest = new DoctorUpdateDTORequest(
                "doctor1","doctor1@gmail.com","gallo", LocalTime.of(9, 0), LocalTime.of(18, 0)
        );

        user = new User();
        user.setEmail(doctorUserRegisterDTORequest.email());

        role = new Roles();
        role.setRole(RolesEnum.DOCTOR);
    }

    @Test
    void findAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor1, doctor2));
        when(doctorMapper.toDoctorDtoResponse(doctor1)).thenReturn(doctorDTOResponse1);
        when(doctorMapper.toDoctorDtoResponse(doctor2)).thenReturn(doctorDTOResponse2);

        List<DoctorDTOResponse> doctorDTOResponses = doctorService.findAllDoctors();

        assertEquals(2, doctorDTOResponses.size());
        assertTrue(doctorDTOResponses.contains(doctorDTOResponse1));
        assertTrue(doctorDTOResponses.contains(doctorDTOResponse2));
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void getOneDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));
        when(doctorMapper.toDoctorDtoResponse(doctor1)).thenReturn(doctorDTOResponse1);

        DoctorDTOResponse response = doctorService.getOneDoctor(1L);

        assertEquals(doctorDTOResponse1, response);
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorMapper, times(1)).toDoctorDtoResponse(doctor1);
    }

    @Test
    void whenGetOneDoctorAndNotFoundThenThrowException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getOneDoctor(1L));
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void deleteDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));
        doNothing().when(doctorRepository).deleteById(1L);

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteDoctorAndNotFoundThenThrowException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteDoctor(1L));
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void registerADoctor() {
        when(userRepository.existsByEmail(doctorUserRegisterDTORequest.email())).thenReturn(false);
        when(passwordEncoder.encode(doctorUserRegisterDTORequest.password())).thenReturn("encodedPassword");
        when(rolesRepository.findByRole(RolesEnum.DOCTOR)).thenReturn(Optional.of(role));

        //  Simular el guardado del usuario y el doctor
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);

        when(doctorMapper.toDoctorDtoResponse(doctor1)).thenReturn(doctorDTOResponse1);

        DoctorDTOResponse response = doctorService.registerADoctor(doctorUserRegisterDTORequest);

        assertEquals(doctorDTOResponse1, response);
        verify(userRepository, times(1)).existsByEmail(doctorUserRegisterDTORequest.email());
        verify(passwordEncoder, times(1)).encode(doctorUserRegisterDTORequest.password());
        verify(rolesRepository, times(1)).findByRole(RolesEnum.DOCTOR);
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any(Doctor.class));
        verify(doctorMapper, times(1)).toDoctorDtoResponse(doctor1);
    }

    @Test
    void whenRegisterADoctorAndUserAlreadyExistsThenThrowException() {
        when(userRepository.existsByEmail(doctorUserRegisterDTORequest.email())).thenReturn(true);

        assertThrows(UserAlreadyRegistered.class, () -> doctorService.registerADoctor(doctorUserRegisterDTORequest));
        verify(userRepository, times(1)).existsByEmail(doctorUserRegisterDTORequest.email());
        verify(passwordEncoder, never()).encode(anyString());
        verify(rolesRepository, never()).findByRole(any());
        verify(userRepository, never()).save(any(User.class));
        verify(doctorRepository, never()).save(any(Doctor.class));
        verify(doctorMapper, never()).toDoctorDtoResponse(any(Doctor.class));
    }

    @Test
    void whenRegisterADoctorAndRoleNotFoundThenThrowException() {
        when(userRepository.existsByEmail(doctorUserRegisterDTORequest.email())).thenReturn(false);
        when(passwordEncoder.encode(doctorUserRegisterDTORequest.password())).thenReturn("encodedPassword");
        when(rolesRepository.findByRole(RolesEnum.DOCTOR)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> doctorService.registerADoctor(doctorUserRegisterDTORequest));
        verify(userRepository, times(1)).existsByEmail(doctorUserRegisterDTORequest.email());
        verify(passwordEncoder, times(1)).encode(doctorUserRegisterDTORequest.password());
        verify(rolesRepository, times(1)).findByRole(RolesEnum.DOCTOR);
        verify(userRepository, never()).save(any(User.class));
        verify(doctorRepository, never()).save(any(Doctor.class));
        verify(doctorMapper, never()).toDoctorDtoResponse(any(Doctor.class));
    }

    @Test
    void updateDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor1));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);
        when(doctorMapper.toDoctorDtoResponse(doctor1)).thenReturn(doctorDTOResponse1);

        DoctorDTOResponse response = doctorService.updateDoctor(1L, doctorUpdateDTORequest);

        assertEquals(doctorDTOResponse1, response);
        assertEquals(doctorUpdateDTORequest.specialty(), doctor1.getSpecialty());
        assertEquals(doctorUpdateDTORequest.avaliable_from(), doctor1.getAvaliable_from());
        assertEquals(doctorUpdateDTORequest.avaliable_to(), doctor1.getAvaliable_to());
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
        verify(doctorMapper, times(1)).toDoctorDtoResponse(doctor1);
    }

    @Test
    void whenUpdateDoctorAndNotFoundThenThrowException() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor(1L, doctorUpdateDTORequest));
        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, never()).save(any(Doctor.class));
        verify(doctorMapper, never()).toDoctorDtoResponse(any(Doctor.class));
    }
}