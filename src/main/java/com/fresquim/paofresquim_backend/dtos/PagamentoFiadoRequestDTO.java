package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

public record PagamentoFiadoRequestDTO(
        Long idVenda,
        Long idCliente,
        BigDecimal valorDevido
) {}
