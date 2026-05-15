package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.CriarProdutoRequestDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoResponseDTO;
import com.fresquim.paofresquim_backend.entity.Produto;
import com.fresquim.paofresquim_backend.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.fresquim.paofresquim_backend.entity.Estoque;
import com.fresquim.paofresquim_backend.repository.EstoqueRepository;
import com.fresquim.paofresquim_backend.repository.ItemVendaRepository;

import java.math.BigDecimal;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final EstoqueRepository estoqueRepository;

    private final ItemVendaRepository itemVendaRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository,
            ItemVendaRepository itemVendaRepository
    ) {

        this.produtoRepository =
                produtoRepository;

        this.estoqueRepository =
                estoqueRepository;

        this.itemVendaRepository =
                itemVendaRepository;
    }

    private Boolean existeProdutoPorNome(String nome) {
        return produtoRepository.findFirstByNomeContainingIgnoreCase(nome).isPresent();
    }

    public Produto recuperaProdutoPorId(Long id) {
        return produtoRepository.getReferenceById(id);
    }

    public ProdutoResponseDTO criar(CriarProdutoRequestDTO produtoDTO) {
        if (!existeProdutoPorNome(produtoDTO.nome())) {
            Produto produto = new Produto(
                    produtoDTO.nome(),
                    produtoDTO.preco(),
                    produtoDTO.unidadeMedida(),
                    produtoDTO.codigoBarras(),
                    produtoDTO.favorito()
            );

            produtoRepository.save(produto);
            return toResponseDTO(produto);
        }

        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe esse produto cadastrado."
        );
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO atualizar(Long id, CriarProdutoRequestDTO produtoDTO) {
        Produto produto = recuperaProdutoPorId(id);

        produto.setNome(produtoDTO.nome());
        produto.setUnidadeMedida(produtoDTO.unidadeMedida());
        produto.setCodigoBarras(produtoDTO.codigoBarras());
        produto.setPreco(produtoDTO.preco());
        produto.setFavorito(produtoDTO.favorito() != null ? produtoDTO.favorito() : false);

        produtoRepository.save(produto);

        return toResponseDTO(produto);
    }

    public ProdutoResponseDTO findProdutoById(Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        return toResponseDTO(produto);
    }

    public void deletar(Long id) {

        Produto produto =
                recuperaProdutoPorId(id);

        Estoque estoque =
                estoqueRepository
                        .findByProdutoId(id)
                        .orElse(null);

        if (
                estoque != null &&
                        estoque.getQuantidadeAtual()
                                .compareTo(BigDecimal.ZERO) > 0
        ) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível excluir o produto pois ele possui estoque."
            );
        }

        boolean possuiVenda =
                itemVendaRepository
                        .existsByProdutoId(id);

        if (possuiVenda) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    """
                    Não é possível excluir o produto pois ele já foi utilizado em vendas.
                    """
            );
        }

        if (estoque != null) {
            estoqueRepository.delete(estoque);
        }

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        var estoque = estoqueRepository
                .findByProdutoId(produto.getId());

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getUnidadeMedida(),
                produto.getCodigoBarras(),
                produto.getFavorito(),
                estoque
                        .map(e -> e.getQuantidadeAtual())
                        .orElse(null)
        );
    }
}