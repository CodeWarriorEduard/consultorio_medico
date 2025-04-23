package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.MedicalRecordRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.MedicalRecordDTOResponse;
import com.rafael.consultorio_medico_actividad.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicalRecordDTOResponse>> getAllMedicalRecords() {
        return new ResponseEntity<>(medicalRecordService.findAllMedicalRecord(), HttpStatus.OK);
    }

    @GetMapping("/{medical_record_id}")
    public ResponseEntity<MedicalRecordDTOResponse> getOneMedicalRecord(@PathVariable Long medical_record_id) {
        return new ResponseEntity<>(medicalRecordService.getOneMedicalRecord(medical_record_id), HttpStatus.OK);
    }

    @GetMapping("/patient/{patient_id}")
    public ResponseEntity<List<MedicalRecordDTOResponse>> getPatientMedicalRecords(@PathVariable Long patient_id) {
        return new ResponseEntity<>(medicalRecordService.findMedicalRecordByPatientId(patient_id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<MedicalRecordDTOResponse> createMedicalRecord(@RequestBody MedicalRecordRegisterDTORequest medical_record) {
        return new ResponseEntity<>(medicalRecordService.registerAMedicalRecord(medical_record), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MedicalRecordDTOResponse> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
