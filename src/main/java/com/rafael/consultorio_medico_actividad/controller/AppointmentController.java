package com.rafael.consultorio_medico_actividad.controller;


import com.rafael.consultorio_medico_actividad.dto.request.AppointmentRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.AppointmentDTOResponse;
import com.rafael.consultorio_medico_actividad.service.interfaces.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDTOResponse>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.findAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{appointment_id}")
    public ResponseEntity<AppointmentDTOResponse> getAppointmentById(@PathVariable Long appointment_id) {
        return new ResponseEntity<>(appointmentService.getOneAppointment(appointment_id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<AppointmentDTOResponse> createAppointment(@RequestBody AppointmentRegisterDTORequest apppointment){
        return new ResponseEntity<>(appointmentService.createAnAppointment(apppointment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTOResponse> deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
