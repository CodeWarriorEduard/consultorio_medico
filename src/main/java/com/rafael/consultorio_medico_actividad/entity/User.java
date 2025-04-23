package com.rafael.consultorio_medico_actividad.entity;

import com.rafael.consultorio_medico_actividad.enumeration.RolesEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long user_id;

    private String full_name;

    private String email;

    private String password;

    @ManyToOne()
    @JoinColumn(
            name = "role_id", referencedColumnName = "role_id"
    )
    private Roles role;

}
