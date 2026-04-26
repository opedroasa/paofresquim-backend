package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.enums.Unidade;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Unidade unidadeMedida,
        String codigoBarras
) { }
