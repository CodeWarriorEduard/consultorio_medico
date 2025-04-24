package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.Patient;
import com.rafael.consultorio_medico_actividad.exception.notFound.PatientNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.PatientMapper;
import com.rafael.consultorio_medico_actividad.repository.PatientRepository;
import com.rafael.consultorio_medico_actividad.service.interfaces.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDTOResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTOResponse getOnePatient(Long id) {
        Patient response = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with id: "+id+" not found"));

        return patientMapper.toPatientDtoResponse(response);
    }

    @Override
    public PatientDTOResponse registerAPatient(PatientRegisterDTORequest patient) {
        return patientMapper.toPatientDtoResponse(patientRepository.save(patientMapper.toPatient(patient)));
    }

    @Override
    public PatientDTOResponse updateAPatient(Long id, PatientRegisterDTORequest data) {
        Patient response = patientRepository.findById(id)
                .orElseThrow(()-> new PatientNotFoundException("Patient with id: "+ id + " not found"));

        response.setFull_name(data.full_name());
        response.setEmail(data.email());
        response.setPhone(data.phone());
        return patientMapper.toPatientDtoResponse(patientRepository.save(response));
    }

    @Override
    public void deleteAPatient(Long id) {
        Patient response = patientRepository.findById(id)
                .orElseThrow(()-> new PatientNotFoundException("Patient with id: "+ id + "not found"));

        // If exists delete
        patientRepository.deleteById(response.getPatient_id());
    }
}
