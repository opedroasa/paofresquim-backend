package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.AtestadoDTO;
import com.fresquim.paofresquim_backend.service.AtestadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atestados")
@CrossOrigin(origins = "http://localhost:5180")
public class AtestadoController {

    private final AtestadoService service;

    public AtestadoController(AtestadoService service) {
        this.service = service;
    }

    @PostMapping
    public AtestadoDTO criar(@RequestBody AtestadoDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<AtestadoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/funcionario/{id}")
    public List<AtestadoDTO> listarPorFuncionario(@PathVariable Integer id) {
        return service.listarPorFuncionario(id);
    }
}