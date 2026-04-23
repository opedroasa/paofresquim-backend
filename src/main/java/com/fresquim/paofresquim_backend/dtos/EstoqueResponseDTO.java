package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

public record EstoqueResponseDTO(
        Long id,
        String name,
        BigDecimal preco,
        BigDecimal quantidadeAtual,
        BigDecimal estoqueMinimo,
        String codigoBarra
) { }
