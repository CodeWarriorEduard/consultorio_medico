package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.update.AppointmentDTOUpdate;
import com.rafael.consultorio_medico_actividad.entity.*;
import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;
import com.rafael.consultorio_medico_actividad.exception.ConsultRoomAlreadyBooked;
import com.rafael.consultorio_medico_actividad.exception.DoctorAppointmentConflictException;
import com.rafael.consultorio_medico_actividad.exception.TimeConflictException;
import com.rafael.consultorio_medico_actividad.exception.notFound.AppointMentNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.ConsultRoomNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.DoctorNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.PatientNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.AppointmentMapper;
import com.rafael.consultorio_medico_actividad.repository.AppointmentRepository;
import com.rafael.consultorio_medico_actividad.repository.ConsultRoomRepository;
import com.rafael.consultorio_medico_actividad.repository.DoctorRepository;
import com.rafael.consultorio_medico_actividad.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    AppointmentMapper appointmentMapper;

    @Mock
    ConsultRoomRepository consultRoomRepository;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    AppointmentServiceImpl appointmentServiceImpl;

    @Test
    void findAllAppointments() {

        Appointment ap1 = createTestAppointment(1L);
        Appointment ap2 = createTestAppointment(2L);

        PatientDTOResponse patientDTOResponse = new PatientDTOResponse(ap1.getPatient().getFull_name());

        AppointmentDTOResponse ap1ResponseDTO = new AppointmentDTOResponse(ap1.getStart_time(),ap1.getEnd_time(), patientDTOResponse);

        AppointmentDTOResponse ap2ResponseDTO = new AppointmentDTOResponse(ap2.getStart_time(),ap2.getEnd_time(), patientDTOResponse);

        when(appointmentRepository.findAll()).thenReturn(List.of(ap1, ap2));
        when(appointmentMapper.toAppointmentDtoResponse(ap1)).thenReturn(ap1ResponseDTO);
        when(appointmentMapper.toAppointmentDtoResponse(ap2)).thenReturn(ap2ResponseDTO);
        List<AppointmentDTOResponse> appointments = appointmentServiceImpl.findAllAppointments();

        assertTrue(appointments.contains(ap1ResponseDTO));
        assertTrue(appointments.contains(ap2ResponseDTO));
        assertEquals(appointments.size(), 2);
        verify(appointmentRepository,times(1)).findAll();

    }

    @Test
    void getOneAppointment() {
        Appointment ap1 = createTestAppointment(1L);

        PatientDTOResponse patientDTOResponse = new PatientDTOResponse(ap1.getPatient().getFull_name());

        AppointmentDTOResponse ap1ResponseDTO = new AppointmentDTOResponse(ap1.getStart_time(),ap1.getEnd_time(), patientDTOResponse);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(ap1));
        when(appointmentMapper.toAppointmentDtoResponse(ap1)).thenReturn(ap1ResponseDTO);

        AppointmentDTOResponse response = appointmentServiceImpl.getOneAppointment(1L);
        assertEquals(response.start_time(),ap1ResponseDTO.start_time());
        verify(appointmentRepository,times(1)).findById(1L);
    }

    @Test
    void whenGetOneAppointmentAndNotFoundThenThrowException() {

        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(AppointMentNotFoundException.class, () -> appointmentServiceImpl.getOneAppointment(1L));
        verify(appointmentRepository,times(1)).findById(any());
    }

    @Test
    void deleteAppointment() {
        when(appointmentRepository.existsById(any())).thenReturn(true);
        appointmentServiceImpl.deleteAppointment(1L);
        verify(appointmentRepository, times(1)).deleteById(any());
    }

    @Test
    void whenDeleteAppointmentAndNotFoundThenThrowException() {
        when(appointmentRepository.existsById(any())).thenReturn(false);
        assertThrows(AppointMentNotFoundException.class, () -> appointmentServiceImpl.deleteAppointment(1L));
        verify(appointmentRepository,times(1)).existsById(any());
    }

    @Test
    void createAnAppointment() {

        Appointment ap1 = createTestAppointment(1L);

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        PatientDTOResponse patientDTOResponse = new PatientDTOResponse(ap1.getPatient().getFull_name());

        AppointmentDTOResponse ap1ResponseDTO = new AppointmentDTOResponse(ap1.getStart_time(),ap1.getEnd_time(), patientDTOResponse);

        when(appointmentMapper.toAppointment(ap1RequestDTO)).thenReturn(ap1);
        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(ap1.getDoctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));
        when(appointmentRepository.save(any())).thenReturn(ap1);
        when(appointmentRepository.findConflict(any(), any(), any())).thenReturn(List.of());
        when(appointmentMapper.toAppointmentDtoResponse(ap1)).thenReturn(ap1ResponseDTO);

        AppointmentDTOResponse response = appointmentServiceImpl.createAnAppointment(ap1RequestDTO);

        assertEquals(response.start_time(),ap1ResponseDTO.start_time());
        verify(appointmentRepository,times(1)).save(any());
    }

    @Test
    void whenCreateAnAppointmentAndPatientNotFoundThenThrowException() {

        //setting variables for the test
        Appointment ap1 = createTestAppointment(1L);

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
        verify(patientRepository,times(1)).findById(any());
    }

    @Test
    void whenCreateAnAppointmentAndDoctorNotFoundThenThrowException(){
        //setting variables for the test
        Appointment ap1 = createTestAppointment(1L);

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));
        assertThrows(DoctorNotFoundException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
        verify(doctorRepository,times(1)).findById(any());
    }

    @Test
    void whenCreateAnAppointmentAndConsultRoomNotFoundThenThrowException(){
        //setting variables for the test
        Appointment ap1 = createTestAppointment(1L);

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ConsultRoomNotFoundException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
        verify(consultRoomRepository,times(1)).findById(any());
    }

    @Test
    void whenCreateAnAppointmentAndEndTimeConflictWithStartTimeThenThrowException() {
        //setting variables for the test
        // when end time < start time
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(LocalDateTime.now().plusHours(1));

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(), ap1.getEnd_time(), 1L, 1L, 1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));


        assertThrows(TimeConflictException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void whenCreateAnAppointmentAndStartTimeIsBeforeNowThenThrowException() {
        //setting variables for the test
        // when start time < now + 1 hour
        Appointment ap1 = createTestAppointment(1L);
        ap1.setStart_time(LocalDateTime.now());

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(), ap1.getEnd_time(), 1L, 1L, 1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));


        assertThrows(TimeConflictException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void whenCreateAnAppointmentAndEndTimeConflictWithDoctorAvaliableToThenThrowException(){
        //setting variables for the test
        // when end time > doctors avaliable to
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_to().plusHours(1).getHour()));

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(ap1.getDoctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void whenCreateAnAppointmentAndStartTimeConflictWithDoctorAvaliableToThenThrowException(){
        //setting variables for the test
        // when end time > doctors avaliable to
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_to().plusHours(1).getHour()));

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(ap1.getDoctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void whenCreateAnAppointmentAndStartTimeConflictWithDoctorAvaliableFromThenThrowException(){
        //setting variables for the test
        // when start time < doctors avaliable from
        Appointment ap1 = createTestAppointment(1L);
        ap1.setStart_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_from().minusHours(1).getHour()));

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(ap1.getDoctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void whenCreateAnAppointmentAndThereIsConflictThenThrowException(){
        //setting variables for the test
        Appointment ap1 = createTestAppointment(1L);

        AppointmentRegisterDTORequest ap1RequestDTO = new AppointmentRegisterDTORequest(ap1.getStart_time(),ap1.getEnd_time(),1L,1L,1L);

        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(any())).thenReturn(Optional.of(ap1.getDoctor()));
        when(consultRoomRepository.findById(any())).thenReturn(Optional.of(new ConsultRoom()));
        when(appointmentRepository.findConflict(any(), any(), any())).thenReturn(List.of(new Appointment()));

        assertThrows(ConsultRoomAlreadyBooked.class, () -> appointmentServiceImpl.createAnAppointment(ap1RequestDTO));
    }

    @Test
    void updateAppointment() {

        Appointment ap1 = createTestAppointment(1L);

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        PatientDTOResponse patientDTOResponse = new PatientDTOResponse(ap1.getPatient().getFull_name());

        AppointmentDTOResponse ap1ResponseDTO = new AppointmentDTOResponse(ap1.getStart_time(),ap1.getEnd_time(), patientDTOResponse);

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));
        when(appointmentMapper.toAppointmentDtoResponse(any())).thenReturn(ap1ResponseDTO);

        AppointmentDTOResponse response = appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO);
        assertEquals(response.patient().full_name(),ap1.getPatient().getFull_name());
        verify(appointmentRepository).save(any());
    }

    @Test
    void whenUpdateAppointmentAndNotFoundThenThrowException(){
        Appointment ap1 = createTestAppointment(1L);

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(AppointMentNotFoundException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndEndTimeConflictWithStartTimeThenThrowException() {
        //setting variables for the test
        // when end time < start time
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(LocalDateTime.now().plusHours(1));

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));

        assertThrows(TimeConflictException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndStartTimeIsBeforeNowThenThrowException() {
        //setting variables for the test
        // when start time < now + 1 hour
        Appointment ap1 = createTestAppointment(1L);
        ap1.setStart_time(LocalDateTime.now());

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));

        assertThrows(TimeConflictException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndEndTimeConflictWithDoctorAvaliableToThenThrowException(){
        //setting variables for the test
        // when end time > doctors avaliable to
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_to().plusHours(1).getHour()));

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndStartTimeConflictWithDoctorAvaliableToThenThrowException(){
        //setting variables for the test
        // when end time > doctors avaliable to
        Appointment ap1 = createTestAppointment(1L);
        ap1.setEnd_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_to().plusHours(1).getHour()));

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndStartTimeConflictWithDoctorAvaliableFromThenThrowException(){
        //setting variables for the test
        // when start time < doctors avaliable from
        Appointment ap1 = createTestAppointment(1L);
        ap1.setStart_time(ap1.getEnd_time().withHour(ap1.getDoctor().getAvaliable_from().minusHours(1).getHour()));

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }

    @Test
    void whenUpdateAnAppointmentAndThereIsConflictThenThrowException(){
        //setting variables for the test
        Appointment ap1 = createTestAppointment(1L);

        AppointmentDTOUpdate ap1UpdateDTO = new AppointmentDTOUpdate(ap1.getStart_time().plusMinutes(30),ap1.getEnd_time(), ap1.getAppointmentStatus());

        when(appointmentRepository.findById(any())).thenReturn(Optional.of(ap1));
        when(appointmentRepository.findConflict(any(), any(), any())).thenReturn(List.of(new Appointment()));

        assertThrows(ConsultRoomAlreadyBooked.class, () -> appointmentServiceImpl.updateAppointment(1L, ap1UpdateDTO));
    }


    Appointment createTestAppointment(Long id){

        Patient patient = Patient.builder()
                .patient_id(1L)
                .full_name("pollo1")
                .build();

        Doctor doctor = Doctor.builder()
                .doctor_id(1L)
                .avaliable_from(LocalTime.of(6,0))
                .avaliable_to(LocalTime.of(20,0))
                .full_name("pollo doc 1")
                .specialty("gallo")
                .build();

        ConsultRoom consultRoom = ConsultRoom.builder()
                .consult_room_id(1L)
                .name("consult room 1")
                .floor(1)
                .build();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .medical_record_id(1L)
                .diagnosis("diagnosis 1")
                .notes("notes 1")
                .build();

        Appointment ap = Appointment.builder()
                .appointment_id(id)
                .patient(patient)
                .doctor(doctor)
                .consult_room(consultRoom)
                .medicalRecord(medicalRecord)
                .appointmentStatus(AppointmentStatus.SCHEDULED)
                .start_time(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0))
                .end_time(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                .build();

        return ap;
    }
}