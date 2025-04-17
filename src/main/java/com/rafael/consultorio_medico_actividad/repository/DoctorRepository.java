package com.rafael.consultorio_medico_actividad.repository;

import com.rafael.consultorio_medico_actividad.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
