package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {

    @Id
    private Long privilege_id;

    private String privilege_name;

    @ManyToMany(mappedBy = "privileges")
    private List<Roles> roles;


}
