package com.rafael.consultorio_medico_actividad.controller;

import com.rafael.consultorio_medico_actividad.dto.request.UserLoginDTORequest;
import com.rafael.consultorio_medico_actividad.dto.response.TokenDTOResponse;
import com.rafael.consultorio_medico_actividad.service.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenDTOResponse login(@RequestBody UserLoginDTORequest loginDetails) {
        return authService.login(loginDetails);
    }
}
