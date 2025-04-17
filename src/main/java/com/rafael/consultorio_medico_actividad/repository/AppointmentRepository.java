package com.rafael.consultorio_medico_actividad.repository;

import com.rafael.consultorio_medico_actividad.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a from Appointment  a WHERE a.consult_room.consult_room_id = : consult_room_id and (a.start_time < :end_time and a.end_time > :start_time)")
    List<Appointment> findConflict(@Param("consult_room_id") Long consult_room_id, @Param("start_time")LocalDateTime start_time, @Param("start_time")LocalDateTime end_time);
}
