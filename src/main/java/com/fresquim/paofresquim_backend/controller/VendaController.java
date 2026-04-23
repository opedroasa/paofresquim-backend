package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.*;
import com.fresquim.paofresquim_backend.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        VendaResponseDTO response = service.registrarVenda(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/processar_pagamento")
    public ResponseEntity<Void> processarPagamento(@RequestBody PagamentoRequestDTO dto) {
        service.processaPagamento(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VendaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodasVendas());
    }
}
