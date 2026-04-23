package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.CriarProdutoRequestDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoResponseDTO;
import com.fresquim.paofresquim_backend.dtos.VendaResponseDTO;
import com.fresquim.paofresquim_backend.entity.Produto;
import com.fresquim.paofresquim_backend.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody CriarProdutoRequestDTO dto) {
        ProdutoResponseDTO response = service.criar(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<ProdutoResponseDTO> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.findProdutoById(id);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<ProdutoResponseDTO> response = service.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody CriarProdutoRequestDTO produto) {
        ProdutoResponseDTO response =  service.atualizar(id,produto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
