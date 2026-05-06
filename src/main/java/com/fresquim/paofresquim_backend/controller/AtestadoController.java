package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.AtestadoResponseDTO;
import com.fresquim.paofresquim_backend.dtos.CriarAtestadoRequestDTO;
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
    public AtestadoResponseDTO criar(@RequestBody CriarAtestadoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<AtestadoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/funcionario/{id}")
    public List<AtestadoResponseDTO> listarPorFuncionario(@PathVariable Integer id) {
        return service.listarPorFuncionario(id);
    }
}