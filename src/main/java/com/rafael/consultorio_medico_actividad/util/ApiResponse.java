package com.rafael.consultorio_medico_actividad.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{

    private int errorCode;
    private String message;
    private T data;
    private List<String> errors;
    private Long timestamp;

}
