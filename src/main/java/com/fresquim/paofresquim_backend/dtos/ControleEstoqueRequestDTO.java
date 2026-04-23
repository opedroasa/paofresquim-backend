package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

public record ControleEstoqueRequestDTO(
        BigDecimal quantidade
) {
}
