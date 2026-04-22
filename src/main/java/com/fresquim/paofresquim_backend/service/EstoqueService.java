package br.com.paofresquim.estoque;

import br.com.paofresquim.produto.Produto;
import br.com.paofresquim.produto.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Estoque criar(Long produtoId, Estoque estoque) {
        Produto produto = buscarProduto(produtoId);

        if (estoqueRepository.existsByProdutoId(produtoId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe controle de estoque para este produto."
            );
        }

        validarEstoque(estoque);

        estoque.setProduto(produto);
        return estoqueRepository.save(estoque);
    }

    @Transactional(readOnly = true)
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estoque não encontrado."
                ));
    }

    @Transactional(readOnly = true)
    public Estoque buscarPorProduto(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estoque não encontrado para o produto informado."
                ));
    }

    @Transactional
    public Estoque atualizar(Long id, Estoque estoqueAtualizado) {
        Estoque estoqueExistente = buscarPorId(id);

        validarEstoque(estoqueAtualizado);

        estoqueExistente.setQuantidadeAtual(estoqueAtualizado.getQuantidadeAtual());
        estoqueExistente.setEstoqueMinimo(estoqueAtualizado.getEstoqueMinimo());

        return estoqueRepository.save(estoqueExistente);
    }

    @Transactional
    public Estoque adicionarQuantidade(Long id, BigDecimal quantidade) {
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade adicionada deve ser maior que zero.");
        }

        Estoque estoque = buscarPorId(id);
        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual().add(quantidade));

        return estoqueRepository.save(estoque);
    }

    @Transactional
    public Estoque removerQuantidade(Long id, BigDecimal quantidade) {
        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade removida deve ser maior que zero.");
        }

        Estoque estoque = buscarPorId(id);

        BigDecimal novoSaldo = estoque.getQuantidadeAtual().subtract(quantidade);
        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para a saída informada.");
        }

        estoque.setQuantidadeAtual(novoSaldo);
        return estoqueRepository.save(estoque);
    }

    @Transactional(readOnly = true)
    public List<Estoque> listarAbaixoDoMinimo() {
        return estoqueRepository.findAll()
                .stream()
                .filter(Estoque::isAbaixoDoMinimo)
                .toList();
    }

    @Transactional
    public void deletar(Long id) {
        Estoque estoque = buscarPorId(id);
        estoqueRepository.delete(estoque);
    }

    private Produto buscarProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado."
                ));
    }

    private void validarEstoque(Estoque estoque) {
        if (estoque.getQuantidadeAtual() == null || estoque.getQuantidadeAtual().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade atual não pode ser negativa.");
        }

        if (estoque.getEstoqueMinimo() == null || estoque.getEstoqueMinimo().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estoque mínimo não pode ser negativo.");
        }
    }
}