package com.rafael.consultorio_medico_actividad.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class TestController {
    @GetMapping()
    public String hola() {
        return "Hola";
    }
}
