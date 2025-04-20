package com.rafael.consultorio_medico_actividad.service.impl;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.dto.update.MedicalRecordUpdateDTO;
import com.rafael.consultorio_medico_actividad.entity.MedicalRecord;
import com.rafael.consultorio_medico_actividad.exception.notFound.MedicalRecordNotFoundException;
import com.rafael.consultorio_medico_actividad.mapper.MedicalRecordMapper;
import com.rafael.consultorio_medico_actividad.repository.MedicalRecordRepository;
import com.rafael.consultorio_medico_actividad.service.MedicalRecordService;

import java.util.List;

public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    public MedicalRecordServiceImpl(MedicalRecordRepository repository, MedicalRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
    public void deleteDoctor(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical record with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public MedicalRecordDTOResponse registerAMedicalRecord(MedicalRecordRegisterDTORequest medical_record) {
         return mapper.toMedicalRecordDTOResponse(repository.save(mapper.toMedicalRecord(medical_record)));
    }

    @Override
    public MedicalRecordDTOResponse updateAMedicalRecord(MedicalRecordUpdateDTO medical_record, Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalRecordNotFoundException("Medical record with id " + id + " not found");
        }

        MedicalRecord response = repository.findById(id).get();
        response.setDiagnosis(medical_record.diagnosis());
        response.setNotes(medical_record.notes());

        return mapper.toMedicalRecordDTOResponse(repository.save(response));
    }
}
