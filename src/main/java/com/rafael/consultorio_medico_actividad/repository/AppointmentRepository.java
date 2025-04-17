package com.rafael.consultorio_medico_actividad.repository;

import com.rafael.consultorio_medico_actividad.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a from Appointment  a WHERE a.consult_room.consult_room_id = : consult_room_id and (a.start_time < :end_time and a.end_time > :start_time)")
    List<Appointment> findConflict();
}
