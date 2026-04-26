package com.fresquim.paofresquim_backend.controller;

import com.fresquim.paofresquim_backend.dtos.ControleEstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.CriarEstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.EstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.EstoqueResponseDTO;
import com.fresquim.paofresquim_backend.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<EstoqueResponseDTO> criar( @RequestBody CriarEstoqueRequestDTO dto) {
        EstoqueResponseDTO estoqueDTO = service.criar(dto);
        return ResponseEntity.ok(estoqueDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EstoqueResponseDTO>> listarTodos() {
        List<EstoqueResponseDTO> estoqueDTO = service.listarTodos();
        return ResponseEntity.ok(estoqueDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> buscarEstoquePorId(@PathVariable Long id) {
        EstoqueResponseDTO estoqueDTO = service.buscarEstoquePorProduto(id);
        return ResponseEntity.ok(estoqueDTO);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorProduto(@PathVariable Long produtoId) {
        EstoqueResponseDTO estoqueDTO = service.buscarEstoquePorProduto(produtoId);
        return ResponseEntity.ok(estoqueDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> atualizar(@PathVariable Long id, @RequestBody EstoqueRequestDTO dto) {
        EstoqueResponseDTO estoqueDTO = service.atualizar(id, dto);
        return ResponseEntity.ok(estoqueDTO);
    }

    @PatchMapping("/{id}/entrada")
    public ResponseEntity<EstoqueResponseDTO> entrada(@PathVariable Long id, @RequestBody ControleEstoqueRequestDTO dto) {
        EstoqueResponseDTO estoqueDTO = service.adicionarQuantidade(id,dto);
        return ResponseEntity.ok(estoqueDTO);
    }

    @PatchMapping("/{id}/saida")
    public ResponseEntity<EstoqueResponseDTO> saida(@PathVariable Long id, @RequestBody ControleEstoqueRequestDTO dto) {
        EstoqueResponseDTO estoqueDTO = service.removerQuantidade(id,dto);
        return ResponseEntity.ok(estoqueDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}