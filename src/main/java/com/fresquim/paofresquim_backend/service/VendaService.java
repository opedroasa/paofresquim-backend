package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.*;
import com.fresquim.paofresquim_backend.entity.*;
import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;
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
    private final PagamentoFiadoRepository pagamentoFiadoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            EstoqueRepository estoqueRepository,
            ItemVendaRepository itemVendaRepository,
            ProdutoRepository produtoRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository,
            PagamentoFiadoRepository pagamentoFiadoRepository) {
        this.vendaRepository = vendaRepository;
        this.estoqueRepository = estoqueRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pagamentoFiadoRepository = pagamentoFiadoRepository;
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

    public void processaPagamento(
            PagamentoRequestDTO pagamentoRequestDTO
    ) {

        Venda venda =
                recuperaVenda(
                        pagamentoRequestDTO.idVenda()
                );

        venda.setTipoPagamento(
                pagamentoRequestDTO.tipoPagamento()
        );

        boolean pago =
                pagamentoRequestDTO.tipoPagamento()
                        !=
                        TipoPagamento.FIADO;

        venda.setStatusPagamento(pago);

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

                    return new VendaResponseDTO(venda.getId(), produtos, idCliente,         venda.getCliente() != null
                            ? venda.getCliente().getNome()
                            : "Consumidor Final", idFuncionario, venda.getValorTotal(), venda.getStatusPagamento());
                })
                .toList();
    }

    public VendaResponseDTO registrarVenda(VendaRequestDTO vendaDTO) {
        Cliente cliente = recuperarClienteId(vendaDTO.idCliente());
        if (

                vendaDTO.tipoPagamento()
                        ==
                        TipoPagamento.FIADO

                        &&

                        cliente != null

                        &&

                        !"ATIVO".equalsIgnoreCase(
                                cliente.getStatusCredito()
                        )

        ) {

            throw new IllegalArgumentException(
                    "Cliente sem crédito para compras fiado."
            );
        }
        Funcionario funcionario = recuperarFuncionarioId(vendaDTO.idFuncionario());
        Venda venda = new Venda(
                LocalDateTime.now(),
                BigDecimal.ZERO,
                vendaDTO.tipoPagamento(),
                false,
                cliente,
                funcionario
        );
        vendaRepository.save(venda);
        if (
                vendaDTO.tipoPagamento()
                        ==
                        TipoPagamento.FIADO
        ) {

            PagamentoFiado fiado =
                    new PagamentoFiado(

                            venda,

                            cliente,

                            BigDecimal.ZERO
                    );

            pagamentoFiadoRepository.save(
                    fiado
            );
        }
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
        if (
                vendaDTO.tipoPagamento()
                        ==
                        TipoPagamento.FIADO
        ) {

            PagamentoFiado fiado =
                    pagamentoFiadoRepository
                            .findByVendaId(
                                    venda.getId()
                            )
                            .orElseThrow();

            fiado.setValorDevido(total);

            pagamentoFiadoRepository.save(
                    fiado
            );
        }
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

        return new VendaResponseDTO(venda.getId(), produtosDTO, idCliente,        venda.getCliente() != null
                ? venda.getCliente().getNome()
                : "Consumidor Final", idFuncionario, total, venda.getStatusPagamento());
    }

    public List<VendaResponseDTO> listarFiadosPendentes() {

        return vendaRepository
                .buscarFiadosPendentes(
                        TipoPagamento.FIADO,
                        false
                )
                .stream()
                .map(venda -> {

                    List<ItemVenda> items =
                            itemVendaRepository
                                    .findByVendaId(
                                            venda.getId()
                                    );

                    List<ProdutoVendaResponseDTO> produtos =
                            items.stream()
                                    .map(item ->
                                            new ProdutoVendaResponseDTO(
                                                    item.getId(),
                                                    item.getProduto().getNome(),
                                                    item.getPrecoUnitario(),
                                                    item.getQuantidade(),
                                                    item.getProduto().getUnidadeMedida(),
                                                    item.getProduto().getCodigoBarras()
                                            )
                                    )
                                    .toList();

                    Long idCliente =
                            venda.getCliente() != null
                                    ? venda.getCliente()
                                    .getIdCliente()
                                    .longValue()
                                    : null;

                    Long idFuncionario =
                            venda.getFuncionario() != null
                                    ? venda.getFuncionario()
                                    .getIdFuncionario()
                                    .longValue()
                                    : null;

                    return new VendaResponseDTO(
                            venda.getId(),
                            produtos,
                            idCliente,
                            venda.getCliente() != null
                            ? venda.getCliente().getNome()
                            : "Consumidor Final",
                            idFuncionario,
                            venda.getValorTotal(),
                            venda.getStatusPagamento()
                    );
                })
                .toList();
    }

    public void quitarFiado(
            Long idVenda
    ) {

        Venda venda =
                vendaRepository
                        .findById(idVenda)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Venda não encontrada"
                                        )
                        );

        venda.setStatusPagamento(true);

        vendaRepository.save(venda);

        PagamentoFiado pagamentoFiado =
                pagamentoFiadoRepository
                        .findByVendaId(idVenda)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Pagamento fiado não encontrado"
                                        )
                        );

        pagamentoFiado.setQuitado(true);

        pagamentoFiado.setDataPagamento(
                LocalDateTime.now()
        );

        pagamentoFiadoRepository.save(
                pagamentoFiado
        );
    }
}
