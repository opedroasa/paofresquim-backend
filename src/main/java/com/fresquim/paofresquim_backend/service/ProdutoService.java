package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.CriarProdutoRequestDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoResponseDTO;
import com.fresquim.paofresquim_backend.entity.Produto;
import com.fresquim.paofresquim_backend.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    private Boolean existeProdutoPorNome(String nome) {
        return produtoRepository.findFirstByNomeContainingIgnoreCase(nome).isPresent();
    }

    public Produto recuperaProdutoPorId(Long id) {
        return produtoRepository.getReferenceById(id);
    }

    public ProdutoResponseDTO criar(CriarProdutoRequestDTO produtoDTO) {
        if (!existeProdutoPorNome(produtoDTO.nome())) {
            Produto produto = new Produto(produtoDTO.nome(), produtoDTO.preco(), produtoDTO.unidadeMedida(), produtoDTO.codigoBarras());
            produtoRepository.save(produto);
            return new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),

                    produto.getUnidadeMedida(),
                    produto.getCodigoBarras()
            );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe esse produto cadastrado."
            );
        }
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream().map(produto ->
                new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getUnidadeMedida(),
                        produto.getCodigoBarras()
                )
        ).toList();
    }

    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome).stream().map(produto ->
                new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getUnidadeMedida(),
                        produto.getCodigoBarras()
                )
        ).toList();
    }

    public ProdutoResponseDTO atualizar(Long id, CriarProdutoRequestDTO produtoDTO) {
        Produto produto = recuperaProdutoPorId(id);

        produto.setNome(produtoDTO.nome());
        produto.setUnidadeMedida(produtoDTO.unidadeMedida());
        produto.setCodigoBarras(produtoDTO.codigoBarras());
        produto.setPreco(produtoDTO.preco());
        produtoRepository.save(produto);
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getUnidadeMedida(),
                produto.getCodigoBarras()
        );
    }

    public ProdutoResponseDTO findProdutoById(Long id) {
       Produto produto = produtoRepository.getReferenceById(id);
        return new ProdutoResponseDTO(produto.getId(),produto.getNome(),produto.getPreco(),produto.getUnidadeMedida(), produto.getCodigoBarras());
    }

    public void deletar(Long id) {
        Produto produto = recuperaProdutoPorId(id);
        produtoRepository.delete(produto);
    }
}
