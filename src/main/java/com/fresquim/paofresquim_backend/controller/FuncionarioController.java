package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.entity.Funcionario;
import com.fresquim.paofresquim_backend.service.FuncionarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return service.criar(funcionario);
    }

    @GetMapping("/{id}")
    public Funcionario buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/listar")
    public List<Funcionario> listar() {
        return service.listar();
    }

    @PutMapping
    public Funcionario atualizar(@RequestBody Funcionario funcionario) {
        return service.atualizar(funcionario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}