package com.fresquim.paofresquim_backend.data.repository;

import com.fresquim.paofresquim_backend.data.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(Integer id);

    List<Cliente> listarTodos();

    void atualizar(Cliente cliente);

    void deletar(Integer id);
}