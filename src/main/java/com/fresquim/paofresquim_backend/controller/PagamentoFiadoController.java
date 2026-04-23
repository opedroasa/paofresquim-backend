package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.PagamentoFiadoRequestDTO;
import com.fresquim.paofresquim_backend.service.PagamentoFiadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pagamentos-fiado")
public class PagamentoFiadoController {

    private final PagamentoFiadoService pagamentoFiadoService;

    public PagamentoFiadoController(PagamentoFiadoService pagamentoFiadoService) {
        this.pagamentoFiadoService = pagamentoFiadoService;
    }

    @PostMapping
    public ResponseEntity<Void> gerarFiado(@RequestBody PagamentoFiadoRequestDTO dto) {
        pagamentoFiadoService.gerarPagamentoFiado(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cliente/{idCliente}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long idCliente) {
        BigDecimal saldo = pagamentoFiadoService.consultarSaldoEmAbertoPorCliente(idCliente);
        return ResponseEntity.ok(saldo);
    }
}
