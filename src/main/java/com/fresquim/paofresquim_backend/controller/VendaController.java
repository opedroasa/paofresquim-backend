package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.*;
import com.fresquim.paofresquim_backend.entity.Venda;
import com.fresquim.paofresquim_backend.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<VendaResponseDTO> criar(@RequestBody VendaRequestDTO dto) {
        VendaResponseDTO response = service.registarVenda(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/processar_pagamento")
    public ResponseEntity<Void> processarPagamento(@RequestBody PagamentoRequestDTO dto) {
        service.processaPagamento(dto);
        return ResponseEntity.noContent().build();
    }
}
