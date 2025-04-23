package com.rafael.consultorio_medico_actividad.repository;

import com.rafael.consultorio_medico_actividad.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

//    Optional<Roles> findRolesByRole(String nombre);

}
