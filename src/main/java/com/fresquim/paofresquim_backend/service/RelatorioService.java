package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.ClienteFiadoDTO;
import com.fresquim.paofresquim_backend.dtos.HistoricoVendaDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoMaisVendidoDTO;
import com.fresquim.paofresquim_backend.dtos.RelatorioResponseDTO;

import com.fresquim.paofresquim_backend.repository.ItemVendaRepository;
import com.fresquim.paofresquim_backend.repository.PagamentoFiadoRepository;
import com.fresquim.paofresquim_backend.repository.VendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private PagamentoFiadoRepository pagamentoFiadoRepository;

    public RelatorioResponseDTO gerarRelatorio(

            String produto,

            LocalDate dataInicial,

            LocalDate dataFinal
    ) {

        LocalDateTime inicio =
                dataInicial != null
                        ? dataInicial.atStartOfDay()
                        : LocalDateTime.of(
                        2000,
                        1,
                        1,
                        0,
                        0
                );

        LocalDateTime fim =
                dataFinal != null
                        ? dataFinal.atTime(
                        23,
                        59,
                        59
                )
                        : LocalDateTime.of(
                        2100,
                        12,
                        31,
                        23,
                        59,
                        59
                );

        String filtroProduto =
                (
                        produto == null
                                ||
                                produto.isBlank()
                )
                        ? "%"
                        : "%" + produto + "%";

        BigDecimal totalVendas =
                vendaRepository.buscarTotalVendas(
                        inicio,
                        fim
                );

        Long totalQuantidade =
                itemVendaRepository.buscarTotalQuantidade(
                        filtroProduto,
                        inicio,
                        fim
                );

        BigDecimal totalFiado =
                pagamentoFiadoRepository.buscarTotalFiado();

        BigDecimal saldoAtual =
                totalVendas.subtract(totalFiado);

        List<HistoricoVendaDTO> vendas =
                itemVendaRepository.buscarHistorico(
                        inicio,
                        fim
                );

        List<ClienteFiadoDTO> clientesFiado =
                pagamentoFiadoRepository.buscarClientesFiado();

        List<ProdutoMaisVendidoDTO> produtosMaisVendidos =
                itemVendaRepository.buscarProdutosMaisVendidos(
                        filtroProduto,
                        inicio,
                        fim
                );

        return new RelatorioResponseDTO(

                totalVendas,

                totalQuantidade,

                totalFiado,

                saldoAtual,

                produtosMaisVendidos,

                vendas,

                clientesFiado
        );
    }
}