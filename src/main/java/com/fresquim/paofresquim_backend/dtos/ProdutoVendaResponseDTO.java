package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.enums.Unidade;

import java.math.BigDecimal;

public record ProdutoVendaResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Long quantidade,
        Unidade unidadeMedida,
        String codigoBarras
) {}
