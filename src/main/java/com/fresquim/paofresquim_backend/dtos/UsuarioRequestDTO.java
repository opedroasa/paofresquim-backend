package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.enums.CargoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @Email(message = "Email inválido")
        @NotBlank(message = "Login é obrigatório")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String senha,
        CargoUsuario cargoUsuario

) {}
