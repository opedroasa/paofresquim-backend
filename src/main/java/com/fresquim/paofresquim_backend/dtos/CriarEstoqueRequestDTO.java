package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

public record CriarEstoqueRequestDTO(
        Long idProduto,
        BigDecimal quantidadeAtual,
        BigDecimal estoqueMinimo
) { }
