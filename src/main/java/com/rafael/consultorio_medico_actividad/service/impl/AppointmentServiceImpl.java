package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Appointment;
import com.rafael.consultorio_medico_actividad.entity.ConsultRoom;
import com.rafael.consultorio_medico_actividad.entity.Doctor;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import com.rafael.consultorio_medico_actividad.enumeration.AppointmentStatus;
import com.rafael.consultorio_medico_actividad.exception.DoctorAppointmentConflict;
import com.rafael.consultorio_medico_actividad.exception.ConsultRoomAlreadyBooked;
import com.rafael.consultorio_medico_actividad.exception.TimeConflictException;
import com.rafael.consultorio_medico_actividad.exception.notFound.AppointMentNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.ResourceNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.AppointmentMapper;
import com.rafael.consultorio_medico_actividad.repository.AppointmentRepository;
import com.rafael.consultorio_medico_actividad.repository.ConsultRoomRepository;
import com.rafael.consultorio_medico_actividad.repository.DoctorRepository;
import com.rafael.consultorio_medico_actividad.repository.PatientRepository;
import com.rafael.consultorio_medico_actividad.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final ConsultRoomRepository consultRoomRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, ConsultRoomRepository consultRoomRepository, AppointmentMapper appointmentMapper, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.consultRoomRepository = consultRoomRepository;
        this.appointmentMapper = appointmentMapper;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<AppointmentDTOResponse> findAllAppointments() {
        return List.of();
    }

    @Override
    public AppointmentDTOResponse getOneAppointment(Long id) {
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {

    }

    @Override
    public AppointmentDTOResponse createAnAppointment(AppointmentRegisterDTORequest appointment) {

        Patient patient = patientRepository.findById(appointment.patient_id())
                .orElseThrow(() -> new ResourceNotFoundException("Pacient not found"));


        ConsultRoom consultRoom = consultRoomRepository.findById(appointment.consult_room_id())
                .orElseThrow(() -> new ResourceNotFoundException("Consult room not found"));


        Doctor doctor = doctorRepository.findById(appointment.doctor_id())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        // Looking for conflicts

        if(appointment.start_time().isBefore(LocalDateTime.now()) || appointment.end_time().isBefore(appointment.start_time())){
            throw new TimeConflictException("Appointment time is wrong");
        }

        List<Appointment> conflicts = appointmentRepository.findConflict(consultRoom.getConsult_room_id() ,appointment.start_time(), appointment.end_time());

        if(!conflicts.isEmpty()){
            throw new ConsultRoomAlreadyBooked("Consult room is already booked!");
        }

        if(appointment.start_time().isAfter(appointment.end_time())){
            throw new DoctorAppointmentConflict("The appointment start time is out of the doctor work schedule");
        }


        Appointment appointment_n = appointmentMapper.toAppointment(appointment);
        appointment_n.setDoctor(doctor);
        appointment_n.setPatient(patient);
        appointment_n.setConsult_room(consultRoom);
        appointment_n.setStart_time(appointment.start_time());
        appointment_n.setEnd_time(appointment.end_time());
        appointment_n.setAppointmentStatus(AppointmentStatus.SCHEDULED);

        return appointmentMapper.toAppointmentDtoResponse(appointment_n);
    }

    @Override
    public AppointmentDTOResponse updateAppointment(Long id) {
        return null;
    }

    @Override
    public AppointmentDTOResponse updateAppointmentStatus(Long id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointMentNotFoundException("Appointment not found"));

        appointment.setAppointmentStatus(status);
        return appointmentMapper.toAppointmentDtoResponse(appointmentRepository.save(appointment));
    }
}
