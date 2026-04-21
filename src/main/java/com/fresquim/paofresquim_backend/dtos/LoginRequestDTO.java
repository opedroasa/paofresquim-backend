package com.fresquim.paofresquim_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Login é obrigatório")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String senha
) {}
