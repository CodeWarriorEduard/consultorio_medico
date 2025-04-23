package com.rafael.consultorio_medico_actividad.repository;

import com.rafael.consultorio_medico_actividad.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    @Query("SELECT r from MedicalRecord r where r.patient =: id")
    List<MedicalRecord> findMedicalRecordByPatientId(@Param("id") Long id);
}
