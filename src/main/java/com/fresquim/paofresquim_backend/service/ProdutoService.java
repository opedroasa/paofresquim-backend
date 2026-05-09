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
        Produto produto = recuperaProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getUnidadeMedida(),
                produto.getCodigoBarras(),
                produto.getFavorito()
        );
    }
}