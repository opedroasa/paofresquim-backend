package br.com.paofresquim.estoque;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/produto/{produtoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Estoque criar(@PathVariable Long produtoId, @RequestBody Estoque estoque) {
        return estoqueService.criar(produtoId, estoque);
    }

    @GetMapping
    public List<Estoque> listarTodos() {
        return estoqueService.listarTodos();
    }

    @GetMapping("/{id}")
    public Estoque buscarPorId(@PathVariable Long id) {
        return estoqueService.buscarPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public Estoque buscarPorProduto(@PathVariable Long produtoId) {
        return estoqueService.buscarPorProduto(produtoId);
    }

    @GetMapping("/abaixo-do-minimo")
    public List<Estoque> listarAbaixoDoMinimo() {
        return estoqueService.listarAbaixoDoMinimo();
    }

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable Long id, @RequestBody Estoque estoque) {
        return estoqueService.atualizar(id, estoque);
    }

    @PatchMapping("/{id}/entrada")
    public Estoque entrada(@PathVariable Long id, @RequestBody Map<String, BigDecimal> body) {
        return estoqueService.adicionarQuantidade(id, body.get("quantidade"));
    }

    @PatchMapping("/{id}/saida")
    public Estoque saida(@PathVariable Long id, @RequestBody Map<String, BigDecimal> body) {
        return estoqueService.removerQuantidade(id, body.get("quantidade"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        estoqueService.deletar(id);
    }
}