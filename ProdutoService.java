package br.com.paofresquim.produto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProdutoService {

    private static final Set<String> UNIDADES_VALIDAS = Set.of("UN", "KG", "G", "L", "ML");

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto criar(Produto produto) {
        validarProduto(produto);

        if (produtoRepository.existsByCodigoBarras(produto.getCodigoBarras())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um produto com este código de barras."
            );
        }

        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado."
                ));
    }

    @Transactional(readOnly = true)
    public Produto buscarPorCodigoBarras(String codigoBarras) {
        return produtoRepository.findByCodigoBarras(codigoBarras)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado para o código de barras informado."
                ));
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        validarProduto(produtoAtualizado);

        if (produtoRepository.existsByCodigoBarrasAndIdNot(produtoAtualizado.getCodigoBarras(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Outro produto já está usando este código de barras."
            );
        }

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setUnidadeMedida(produtoAtualizado.getUnidadeMedida());
        produtoExistente.setCodigoBarras(produtoAtualizado.getCodigoBarras());

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do produto é obrigatório.");
        }

        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço deve ser maior que zero.");
        }

        if (produto.getCodigoBarras() == null || produto.getCodigoBarras().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O código de barras é obrigatório.");
        }

        if (produto.getUnidadeMedida() == null || produto.getUnidadeMedida().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A unidade de medida é obrigatória.");
        }

        String unidade = produto.getUnidadeMedida().trim().toUpperCase();
        if (!UNIDADES_VALIDAS.contains(unidade)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unidade de medida inválida. Use: UN, KG, G, L ou ML."
            );
        }

        produto.setNome(produto.getNome().trim());
        produto.setCodigoBarras(produto.getCodigoBarras().trim());
        produto.setUnidadeMedida(unidade);
    }
}
