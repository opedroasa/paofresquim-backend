package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.data.entities.Cliente;
import com.fresquim.paofresquim_backend.domain.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return service.criarCliente(cliente);
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Integer id) {
        return service.buscarCliente(id);
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarClientes();
    }

    @PutMapping
    public Cliente atualizar(@RequestBody Cliente cliente) {
        return service.atualizarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletarCliente(id);
    }
}