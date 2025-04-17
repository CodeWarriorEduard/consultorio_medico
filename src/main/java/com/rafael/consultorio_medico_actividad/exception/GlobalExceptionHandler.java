package com.rafael.consultorio_medico_actividad.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = ResourceNotFoundException.class)
//    public ResponseEntity<DefaultApiResponse<Object>> aerolineaNotFoundExceptionHandler(AerolineaNotFoundException e){
//        DefaultApiResponse<Object> apiResponse = new DefaultApiResponse<>(
//                "404 NOT FOUND",
//                e.getMessage(),
//                null,
//                null
//        );
//        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
//    }

}
