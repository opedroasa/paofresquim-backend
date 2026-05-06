package com.fresquim.paofresquim_backend.dtos;

import java.time.LocalDateTime;

public record AtestadoResponseDTO(
        Integer id,
        Integer funcionarioId,
        String nomeFuncionario,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim
) {
}