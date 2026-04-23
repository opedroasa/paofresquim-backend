package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;

public record PagamentoRequestDTO(
        Long idVenda,
        TipoPagamento tipoPagamento
) {}
