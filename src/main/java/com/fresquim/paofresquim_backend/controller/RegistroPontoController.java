package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.LoginRequest;
import com.fresquim.paofresquim_backend.entity.RegistroPonto;
import com.fresquim.paofresquim_backend.service.RegistroPontoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ponto")
@CrossOrigin(origins = "*")
public class RegistroPontoController {

    private final RegistroPontoService service;

    public RegistroPontoController(RegistroPontoService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public RegistroPonto registrar(@RequestBody LoginRequest request) {
        return service.registrar(request.getCpf(), request.getSenha());
    }

    @GetMapping
    public List<RegistroPonto> listar() {
        return service.listar();
    }
}