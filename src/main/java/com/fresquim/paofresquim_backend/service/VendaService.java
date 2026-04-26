package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.*;
import com.fresquim.paofresquim_backend.entity.*;
import com.fresquim.paofresquim_backend.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueRepository estoqueRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public VendaService(
            VendaRepository vendaRepository,
            EstoqueRepository estoqueRepository,
            ItemVendaRepository itemVendaRepository,
            ProdutoRepository produtoRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository) {
        this.vendaRepository = vendaRepository;
        this.estoqueRepository = estoqueRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    private Produto recuperarProduto(Long id) { return produtoRepository.findById(id).orElseThrow();}
    private Venda recuperaVenda(Long id) { return  vendaRepository.getReferenceById(id);}

    private Estoque validarEstoque(Long id) {
        return estoqueRepository.findByProdutoId(id).orElseThrow();
    }

    private Cliente recuperarClienteId(Long id) {
        if (id == null) return null;
        return clienteRepository.findById(id.intValue()).orElse(null);
    }

    private Funcionario recuperarFuncionarioId(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id.intValue()).orElse(null);
    }
    public void atualizarEstoque(Estoque estoque, Long quantidade){
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual().subtract(new BigDecimal(quantidade)));
            estoqueRepository.save(estoque);
    }

    public void processaPagamento(PagamentoRequestDTO pagamentoRequestDTO) {
        Venda venda = recuperaVenda(pagamentoRequestDTO.idVenda());
        venda.setTipoPagamento(pagamentoRequestDTO.tipoPagamento());
        venda.setStatusPagamento(true);
        vendaRepository.save(venda);
    }

    public List<VendaResponseDTO> listarTodasVendas() {
        return vendaRepository.findAll().stream()
                .map(venda -> {
                    List<ItemVenda> items = itemVendaRepository.findByVendaId(venda.getId());

                    List<ProdutoVendaResponseDTO> produtos = items.stream()
                            .map(item -> new ProdutoVendaResponseDTO(
                                    item.getId(),
                                    item.getProduto().getNome(),
                                    item.getPrecoUnitario(),
                                    item.getQuantidade(),
                                    item.getProduto().getUnidadeMedida(),
                                    item.getProduto().getCodigoBarras()))
                            .toList();

                    Long idCliente = venda.getCliente() != null ? venda.getCliente().getIdCliente().longValue() : null;
                    Long idFuncionario = venda.getFuncionario() != null ? venda.getFuncionario().getIdFuncionario().longValue() : null;

                    return new VendaResponseDTO(venda.getId(), produtos, idCliente, idFuncionario, venda.getValorTotal(), venda.getStatusPagamento());
                })
                .toList();
    }

    public VendaResponseDTO registrarVenda(VendaRequestDTO vendaDTO) {
        Cliente cliente = recuperarClienteId(vendaDTO.idCliente());
        Funcionario funcionario = recuperarFuncionarioId(vendaDTO.idFuncionario());
        Venda venda = new Venda(LocalDateTime.now(),BigDecimal.valueOf(0.0), null,false, cliente, funcionario);
        vendaRepository.save(venda);
        List<ItemVenda> items = new ArrayList<>();

        vendaDTO.products().forEach(product -> {
            Produto recuperarProduto = recuperarProduto(product.id());
            Estoque estoque = validarEstoque(recuperarProduto.getId());
            if (estoque.getQuantidadeAtual().longValue() < product.quantidade()) {
                throw new IllegalArgumentException("Estoque indisponivel para o item" + recuperarProduto.getNome());
            } else {
                BigDecimal subTotal = recuperarProduto.getPreco().multiply(new BigDecimal(product.quantidade()));
                ItemVenda item = new ItemVenda(
                        product.quantidade(),
                        recuperarProduto.getPreco(),
                        subTotal,
                        venda,
                        recuperarProduto
                );

                ItemVenda saves = itemVendaRepository.save(item);
                items.add(saves);
                atualizarEstoque(estoque, product.quantidade());
            }
        });

        BigDecimal total = items.stream()
                .map(ItemVenda::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venda.setValorTotal(total);
        vendaRepository.save(venda);
        Long idCliente = venda.getCliente() != null ? venda.getCliente().getIdCliente().longValue() : null;
        Long idFuncionario = venda.getFuncionario() != null ? venda.getFuncionario().getIdFuncionario().longValue() : null;
        List<ProdutoVendaResponseDTO> produtosDTO = items.stream()
                .map(item -> new ProdutoVendaResponseDTO(
                        item.getId(),
                        item.getProduto().getNome(),
                        item.getPrecoUnitario(),
                        item.getQuantidade(),
                        item.getProduto().getUnidadeMedida(),
                        item.getProduto().getCodigoBarras()))
                .toList();

        return new VendaResponseDTO(venda.getId(), produtosDTO, idCliente, idFuncionario, total, venda.getStatusPagamento());
    }
}
