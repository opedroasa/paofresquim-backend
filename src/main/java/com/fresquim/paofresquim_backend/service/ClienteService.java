package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.entity.Cliente;
import com.fresquim.paofresquim_backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente criarCliente(Cliente cliente) {

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }

        cliente.setStatusCredito("ATIVO");

        return repository.save(cliente);
    }

    public Cliente buscarCliente(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Cliente atualizarCliente(Cliente cliente) {
        if (cliente.getIdCliente() == null) {
            throw new IllegalArgumentException("ID obrigatório");
        }
        return repository.save(cliente);
    }

    public void deletarCliente(Integer id) {
        repository.deleteById(id);
    }
}