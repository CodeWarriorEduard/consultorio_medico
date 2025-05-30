package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Appointment;
import com.rafael.consultorio_medico_actividad.entity.MedicalRecord;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import com.rafael.consultorio_medico_actividad.exception.notFound.AppointMentNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.MedicalRecordNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.PatientNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.MedicalRecordMapper;
import com.rafael.consultorio_medico_actividad.repository.AppointmentRepository;
import com.rafael.consultorio_medico_actividad.repository.MedicalRecordRepository;
import com.rafael.consultorio_medico_actividad.repository.PatientRepository;
import com.rafael.consultorio_medico_actividad.service.interfaces.AppointmentService;
import com.rafael.consultorio_medico_actividad.service.interfaces.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper mapper;
    private final AppointmentService appointmentService;
    private final PatientRepository patientRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository repository, MedicalRecordMapper mapper, AppointmentRepository appointmentRepository, AppointmentService appointmentService, PatientRepository patientRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<MedicalRecordDTOResponse> findAllMedicalRecord() {
        return repository.findAll()
                .stream()
                .map(mapper::toMedicalRecordDTOResponse)
                .toList();
    }

    @Override
    public MedicalRecordDTOResponse getOneMedicalRecord(Long id) {
        return repository.findById(id)
                .map(mapper::toMedicalRecordDTOResponse)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record with id " + id + " not found"));
    }

    @Override
    public void deleteMedicalRecord(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical record with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public MedicalRecordDTOResponse registerAMedicalRecord(MedicalRecordRegisterDTORequest medical_record) {

        Appointment appointment = appointmentRepository.findById(medical_record.appointment_id())
                .orElseThrow(() -> new AppointMentNotFoundException("Appointment with id " + medical_record.appointment_id() + " not found"));

        Patient patient = patientRepository.findById(medical_record.patient_id())
                .orElseThrow(() -> new PatientNotFoundException("Patient with id " + medical_record.patient_id() + " not found"));


        MedicalRecord medicalRecord = MedicalRecord.builder()
                .diagnosis(medical_record.diagnosis())
                .notes(medical_record.notes())
                .patient(patient)
                .appointment(appointment)
                .created_at(medical_record.created_at())
                .build();


         return mapper.toMedicalRecordDTOResponse(repository.save(medicalRecord));
    }

    @Override
    public List<MedicalRecordDTOResponse> findMedicalRecordByPatientId(Long id) {
        return repository.findMedicalRecordByPatientId(id)
                .stream().map(mapper::toMedicalRecordDTOResponse)
                .collect(Collectors.toList());
    }
}
