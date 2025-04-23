package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.ConsultRoomRegisterDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.ConsultRoomDTOResponse;
import com.rafael.consultorio_medico_actividad.service.ConsultRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.endpoint.global}"+"/consult-room")
public class ConsultRoomController {

    private final ConsultRoomService consultRoomService;

    public ConsultRoomController(ConsultRoomService consultRoomService) {
        this.consultRoomService = consultRoomService;
    }


    @GetMapping("/all")
    private ResponseEntity<List<ConsultRoomDTOResponse>> getAllConsultRoooms() {
        return new ResponseEntity<>(consultRoomService.findAllConsultRooms(), HttpStatus.OK);
    }

    @GetMapping("/{consult_room_id}")
    private ResponseEntity<ConsultRoomDTOResponse> getOneConsultRoom(@PathVariable Long consult_room_id) {
        return new ResponseEntity<>(consultRoomService.getOneConsultRoom(consult_room_id), HttpStatus.OK);
    }

    @PostMapping("/new")
    private ResponseEntity<ConsultRoomDTOResponse> registerAConsultRoom(@RequestBody ConsultRoomRegisterDTORequest consult_room) {
        return new ResponseEntity<>(consultRoomService.registerAConsultRoom(consult_room), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ConsultRoomDTOResponse> updateAConsultRoom(@PathVariable  Long id, @RequestBody ConsultRoomRegisterDTORequest consult_room) {
        return new ResponseEntity<>(consultRoomService.updateConsultRoom(id, consult_room), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ConsultRoomDTOResponse> deleteAConsultRoom(@PathVariable  Long id) {
        consultRoomService.deleteAConsultRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
