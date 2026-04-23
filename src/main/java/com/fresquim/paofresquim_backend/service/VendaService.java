package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.PagamentoRequestDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoResponseDTO;
import com.fresquim.paofresquim_backend.dtos.VendaRequestDTO;
import com.fresquim.paofresquim_backend.dtos.VendaResponseDTO;
import com.fresquim.paofresquim_backend.entity.*;
import com.fresquim.paofresquim_backend.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private VendaRepository vendaRepository;
    private EstoqueRepository estoqueRepository;
    private ItemVendaRepository itemVendaRepository;
    private ProdutoRepository produtoRepository;
    private ClienteRepository clienteRepository;
    private FuncionarioRepository funcionarioRepository;

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
        return clienteRepository.findById(id.intValue()).orElse(null);
    }

    private Funcionario recuperarFuncionarioId(Long id) { return funcionarioRepository.findById(id.intValue()).orElse(null); }

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

    public VendaResponseDTO registarVenda(VendaRequestDTO vendaDTO) {
        Cliente cliente = recuperarClienteId(vendaDTO.idCliente());
        Funcionario funcionario = recuperarFuncionarioId(vendaDTO.idFuncionario());
        Venda venda = new Venda(LocalDateTime.now(), BigDecimal.valueOf(0.0), cliente, funcionario);
        List<ItemVenda> items = new ArrayList<>();

        vendaDTO.products().forEach(product -> {
            Produto recuperarProduto = recuperarProduto(product.id());
            Estoque estoque = validarEstoque(recuperarProduto.getId());
            if (estoque.getQuantidadeAtual().longValue() < product.quantidade()) {
                throw new IllegalArgumentException("Estoque indisponivel para o item" + recuperarProduto.getNome());
            } else {
                BigDecimal subTotal = recuperarProduto.getPreco().multiply(new BigDecimal(product.quantidade()));
                ItemVenda item = new ItemVenda(product.quantidade(), recuperarProduto.getPreco(), subTotal, venda, recuperarProduto);
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
        List<ProdutoResponseDTO> produtosDTO = items.stream().
                map( item -> new ProdutoResponseDTO(item.getId(),item.getProduto().getNome(),item.getPrecoUnitario())).
                toList();

        return new VendaResponseDTO(venda.getId(), produtosDTO, idCliente, idFuncionario, total);
    }
}
