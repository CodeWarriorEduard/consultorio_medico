package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.UserLoginDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.UserLoginDTOResponse;
import com.rafael.consultorio_medico_actividad.service.AuthService;
import com.rafael.consultorio_medico_actividad.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    //DELETE THIS METHOD
    @PostMapping("/admin-new")
    public String adminNew() {
        authService.createAdmin();
        return "Admin created successfully";
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/login")
    public UserLoginDTOResponse login(@RequestBody UserLoginDTORequest loginDetails) {
        System.out.println("HOLAAAAAAAA");
        System.out.println(loginDetails);
        return authService.login(loginDetails);
    }
}
