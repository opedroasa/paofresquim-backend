package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.Produto;

import java.math.BigDecimal;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        List<ProdutoVendaResponseDTO> produtos,
        Long idCliente,
        Long idFuncionario,
        BigDecimal subTotal,
        boolean statusPagamento
) {}
