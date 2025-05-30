package com.rafael.consultorio_medico_actividad.entity;

import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long role_id;

    private RolesEnum role;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
