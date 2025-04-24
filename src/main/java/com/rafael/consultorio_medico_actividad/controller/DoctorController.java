package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.DoctorUpdateDTORequest;
import com.rafael.consultorio_medico_actividad.dto.request.DoctorUserRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.DoctorDTOResponse;
import com.rafael.consultorio_medico_actividad.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/all")
    private ResponseEntity<List<DoctorDTOResponse>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.findAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/{doctor_id}")
    private ResponseEntity<DoctorDTOResponse> getOneDoctorById(@PathVariable  Long doctor_id) {
        return new ResponseEntity<>(doctorService.getOneDoctor(doctor_id), HttpStatus.OK);
    }

    @PostMapping("/new")
    private ResponseEntity<DoctorDTOResponse> createDoctor(@RequestBody DoctorUserRegisterDTORequest doctor) {
        System.out.println(doctor);
        return new ResponseEntity<>(doctorService.registerADoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<DoctorDTOResponse> updateDoctor(@PathVariable  Long id, @RequestBody DoctorUpdateDTORequest doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<DoctorDTOResponse> deleteDoctor(@PathVariable  Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
