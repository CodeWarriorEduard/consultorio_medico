package com.rafael.consultorio_medico_actividad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ConsultRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consult_room_id;

    private String name;

    private Integer floor;

    private String description;

    @OneToMany(mappedBy = "consult_room")
    private List<ConsultRoom> consult_rooms;
}
