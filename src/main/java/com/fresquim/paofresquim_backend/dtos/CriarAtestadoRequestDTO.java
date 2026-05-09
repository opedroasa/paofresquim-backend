package com.fresquim.paofresquim_backend.dtos;

import java.time.LocalDateTime;

public record CriarAtestadoRequestDTO(
        Integer funcionarioId,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {
}