package com.rafael.consultorio_medico_actividad.exception;

import com.rafael.consultorio_medico_actividad.exception.notFound.AppointMentNotFoundException;
import com.rafael.consultorio_medico_actividad.exception.notFound.ResourceNotFoundException;
import com.rafael.consultorio_medico_actividad.util.ApiResponse;
import com.rafael.consultorio_medico_actividad.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException .class)
    public ResponseEntity<ApiResponse<Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(404, "Resource not Found", Collections.singletonList(e.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConsultRoomAlreadyBooked .class)
    public ResponseEntity<ApiResponse<Object>> consultRoomAlreeadyBookedExceptionHandler(ConsultRoomAlreadyBooked e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(409,  "Conflict in request", Collections.singletonList(e.getMessage())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DoctorAppointmentConflictException.class)
    public ResponseEntity<ApiResponse<Object>> doctorAppointmentConflictExceptionHandler(DoctorAppointmentConflictException e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(409, "Conflict in requestt", Collections.singletonList(e.getMessage())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TimeConflictException.class)
    public ResponseEntity<ApiResponse<Object>> timeConflictExceptionHandler(TimeConflictException e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(409, "Conflict in request", Collections.singletonList(e.getMessage())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserAlreadyRegistered.class)
    public ResponseEntity<ApiResponse<Object>> userAlreadyRegisteredHandler(UserAlreadyRegistered e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(409, "Conflict in request", Collections.singletonList(e.getMessage())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserLoginException.class)
    public ResponseEntity<ApiResponse<Object>> userLoginExceptionHandler(UserLoginException e){
        return new ResponseEntity<>(ResponseUtil.errorResponse(401, "Bad credentials", Collections.singletonList(e.getMessage())), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> validationExceptionHandler(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ResponseUtil.errorResponse(400, "Bad request", errors), HttpStatus.BAD_REQUEST);
    }


}
