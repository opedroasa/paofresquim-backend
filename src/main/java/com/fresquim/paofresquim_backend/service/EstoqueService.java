package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.ControleEstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.CriarEstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.EstoqueRequestDTO;
import com.fresquim.paofresquim_backend.dtos.EstoqueResponseDTO;
import com.fresquim.paofresquim_backend.entity.Estoque;
import com.fresquim.paofresquim_backend.entity.Produto;
import com.fresquim.paofresquim_backend.repository.ProdutoRepository;
import com.fresquim.paofresquim_backend.repository.EstoqueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository,
                          ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    private Produto recuperaProdutoPorId(Long produtoId) {
        return produtoRepository.getReferenceById(produtoId);
    }
    private Estoque buscarEstoquePorIdProduto(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId).orElse(null);
    }

    private Estoque buscarEstoquePorId(Long estoqueId) {
        return estoqueRepository.getReferenceById(estoqueId);
    }

    public EstoqueResponseDTO criar(CriarEstoqueRequestDTO criarEstoqueRequestDTO) {
        Produto produto = recuperaProdutoPorId(criarEstoqueRequestDTO.idProduto());
        Estoque estoque = buscarEstoquePorIdProduto(criarEstoqueRequestDTO.idProduto());

        if (estoque == null) {
            Estoque estoqueDTO = new Estoque();
            estoqueDTO.setProduto(produto);
            estoqueDTO.setQuantidadeAtual(criarEstoqueRequestDTO.quantidadeAtual());
            estoqueDTO.setEstoqueMinimo(criarEstoqueRequestDTO.estoqueMinimo());
            estoqueRepository.save(estoqueDTO);
            return new EstoqueResponseDTO(
                    estoqueDTO.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    estoqueDTO.getQuantidadeAtual(),
                    estoqueDTO.getEstoqueMinimo(),
                    produto.getCodigoBarras());
        } else {
            throw new IllegalArgumentException("Estoque já existe");
        }
    }

    public List<EstoqueResponseDTO> listarTodos() {
        return estoqueRepository.findAll().stream().map(estoque ->
                new EstoqueResponseDTO(
                        estoque.getId(),
                        estoque.getProduto().getNome(),
                        estoque.getProduto().getPreco(),
                        estoque.getQuantidadeAtual(),
                        estoque.getEstoqueMinimo(),
                        estoque.getProduto().getCodigoBarras())
        ).toList();
    }

    public EstoqueResponseDTO buscarPorId(Long id) {
        return estoqueRepository.findById(id).map(estoque ->
                new EstoqueResponseDTO(
                        estoque.getId(),
                        estoque.getProduto().getNome(),
                        estoque.getProduto().getPreco(),
                        estoque.getQuantidadeAtual(),
                        estoque.getEstoqueMinimo(),
                        estoque.getProduto().getCodigoBarras()
                )).orElseThrow();
    }

    public EstoqueResponseDTO buscarEstoquePorProduto(Long produtoId) {
        Produto produto = recuperaProdutoPorId(produtoId);
        Estoque estoque = buscarEstoquePorIdProduto(produtoId);
        return new EstoqueResponseDTO(
                estoque.getId(),
                produto.getNome(),
                produto.getPreco(),
                estoque.getQuantidadeAtual(),
                estoque.getEstoqueMinimo(),
                produto.getCodigoBarras());
    }

    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO estoqueDto) {
        Estoque estoque = buscarEstoquePorId(id);
        estoque.setProduto(estoque.getProduto());
        estoque.setEstoqueMinimo(estoqueDto.estoqueMinimo());
        estoque.setQuantidadeAtual(estoqueDto.quantidadeAtual());
        estoqueRepository.save(estoque);
        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getProduto().getNome(),
                estoque.getProduto().getPreco(),
                estoque.getQuantidadeAtual(),
                estoque.getEstoqueMinimo(),
                estoque.getProduto().getCodigoBarras());
    }


    public EstoqueResponseDTO adicionarQuantidade(Long id, ControleEstoqueRequestDTO controleEstoque) {
        if (controleEstoque.quantidade() == null || controleEstoque.quantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade adicionada deve ser maior que zero.");
        }

        Estoque estoque = buscarEstoquePorId(id);
        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual().add(controleEstoque.quantidade()));

        estoqueRepository.save(estoque);

        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getProduto().getNome(),
                estoque.getProduto().getPreco(),
                estoque.getQuantidadeAtual(),
                estoque.getEstoqueMinimo(),
                estoque.getProduto().getCodigoBarras());
    }

    public EstoqueResponseDTO removerQuantidade(Long id, ControleEstoqueRequestDTO controleEstoque) {
        if (controleEstoque.quantidade() == null || controleEstoque.quantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade removida deve ser maior que zero.");
        }

        Estoque estoque = buscarEstoquePorId(id);

        BigDecimal novoSaldo = estoque.getQuantidadeAtual().subtract(controleEstoque.quantidade());

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente. Saldo atual: " + estoque.getQuantidadeAtual());
        }

        if (novoSaldo.compareTo(estoque.getEstoqueMinimo()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Remoção deixaria o estoque abaixo do mínimo permitido: " + estoque.getEstoqueMinimo());
        }

        estoque.setQuantidadeAtual(novoSaldo);
        estoqueRepository.save(estoque);

        return new EstoqueResponseDTO(
                estoque.getId(),
                estoque.getProduto().getNome(),
                estoque.getProduto().getPreco(),
                estoque.getQuantidadeAtual(),
                estoque.getEstoqueMinimo(),
                estoque.getProduto().getCodigoBarras());
    }

    public void deletar(Long id) {
        Estoque estoque = buscarEstoquePorId(id);
        estoqueRepository.delete(estoque);
    }
}