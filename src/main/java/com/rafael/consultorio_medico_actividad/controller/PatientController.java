package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.PatientRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.PatientDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    private ResponseEntity<List<PatientDTOResponse>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping("/{patient_id}")
    private ResponseEntity<PatientDTOResponse> getOnePatient(@PathVariable Long patient_id) {
        return new ResponseEntity<>(patientService.getOnePatient(patient_id), HttpStatus.OK);
    }

    @PostMapping("/new")
    private ResponseEntity<PatientDTOResponse> registerAPatient(@RequestBody PatientRegisterDTORequest patient) {
        return new ResponseEntity<>(patientService.registerAPatient(patient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<PatientDTOResponse> updatePatient(@PathVariable  Long id, @RequestBody PatientRegisterDTORequest patient) {
        return new ResponseEntity<>(patientService.updateAPatient(id, patient), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<PatientDTOResponse> deletePatient(@PathVariable  Long id) {
        patientService.deleteAPatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
