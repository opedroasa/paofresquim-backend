package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.LoginRequestDTO;
import com.fresquim.paofresquim_backend.dtos.LoginResponseDTO;
import com.fresquim.paofresquim_backend.dtos.UsuarioRequestDTO;
import com.fresquim.paofresquim_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Void> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        usuarioService.criar(dto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> autenticar(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = usuarioService.autenticar(dto);
        return ResponseEntity.ok(response);
    }
}
