package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.RelatorioResponseDTO;
import com.fresquim.paofresquim_backend.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<RelatorioResponseDTO> gerarRelatorio(
            @RequestParam(required = false)
            String produto,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicial,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFinal
    ) {

        RelatorioResponseDTO response =
                relatorioService.gerarRelatorio(
                        produto,
                        dataInicial,
                        dataFinal
                );

        return ResponseEntity.ok(response);
    }
}