package com.fresquim.paofresquim_backend.dtos;

// vai virar um return do token após implementação do security

public record LoginResponseDTO(
        Long id,
        String nome,
        String login,
        String role
) {}
