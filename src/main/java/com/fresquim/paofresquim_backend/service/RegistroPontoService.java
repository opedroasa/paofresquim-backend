package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.entity.Funcionario;
import com.fresquim.paofresquim_backend.entity.RegistroPonto;
import com.fresquim.paofresquim_backend.repository.FuncionarioRepository;
import com.fresquim.paofresquim_backend.repository.RegistroPontoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroPontoService {

    private final FuncionarioRepository funcionarioRepository;
    private final RegistroPontoRepository registroRepository;

    public RegistroPontoService(FuncionarioRepository funcionarioRepository,
                                RegistroPontoRepository registroRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.registroRepository = registroRepository;
    }

    public RegistroPonto registrar(String cpf, String senha) {

        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (!funcionario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha inválida");
        }

        RegistroPonto registro = new RegistroPonto(
                funcionario.getNome(),
                funcionario.getCpf()
        );

        return registroRepository.save(registro);
    }

    public List<RegistroPonto> listar() {
        return registroRepository.findAll();
    }
}