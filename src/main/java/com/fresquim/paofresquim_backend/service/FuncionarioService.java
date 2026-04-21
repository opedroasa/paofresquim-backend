package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.entity.Funcionario;
import com.fresquim.paofresquim_backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public Funcionario criar(Funcionario funcionario) {

        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }

        return repository.save(funcionario);
    }

    public Funcionario buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public List<Funcionario> listar() {
        return repository.findAll();
    }

    public Funcionario atualizar(Funcionario funcionario) {
        if (funcionario.getIdFuncionario() == null) {
            throw new IllegalArgumentException("ID obrigatório");
        }
        return repository.save(funcionario);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}