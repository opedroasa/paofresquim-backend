package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.LoginRequestDTO;
import com.fresquim.paofresquim_backend.dtos.LoginResponseDTO;
import com.fresquim.paofresquim_backend.dtos.UsuarioRequestDTO;
import com.fresquim.paofresquim_backend.entity.Usuario;
import com.fresquim.paofresquim_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criar(UsuarioRequestDTO dto) {

        usuarioRepository.findByLogin(dto.email())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Login já cadastrado");
                });

        Usuario usuario = Usuario.criar(
                dto.nome(),
                dto.email(),
                dto.senha(),
                dto.cargoUsuario()
        );

        usuarioRepository.save(usuario);
    }

    public LoginResponseDTO autenticar(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(dto.senha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getRole()
        );
    }
}
