package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoricoVendaDTO(
        Long id,
        LocalDateTime data,
        String produto,
        Long quantidade,
        BigDecimal valor
) {
}
