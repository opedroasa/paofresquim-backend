package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoricoVendaDTO(

        Long idVenda,

        LocalDateTime dataVenda,

        String nomeCliente,

        Long quantidadeItens,

        BigDecimal valorTotal

) {
}