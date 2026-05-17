package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.DashboardResponseDTO;
import com.fresquim.paofresquim_backend.dtos.VendaPorDiaDTO;

import com.fresquim.paofresquim_backend.repository.ItemVendaRepository;
import com.fresquim.paofresquim_backend.repository.PagamentoFiadoRepository;
import com.fresquim.paofresquim_backend.repository.VendaRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final VendaRepository vendaRepository;

    private final PagamentoFiadoRepository pagamentoFiadoRepository;

    private final ItemVendaRepository itemVendaRepository;

    public DashboardService(

            VendaRepository vendaRepository,

            PagamentoFiadoRepository pagamentoFiadoRepository,

            ItemVendaRepository itemVendaRepository

    ) {

        this.vendaRepository = vendaRepository;

        this.pagamentoFiadoRepository = pagamentoFiadoRepository;

        this.itemVendaRepository = itemVendaRepository;
    }

    public DashboardResponseDTO buscarDashboard() {

        BigDecimal valorVendasHoje =
                vendaRepository.buscarValorVendasHoje();

        Long quantidadeVendasHoje =
                vendaRepository.buscarQuantidadeVendasHoje();

        Long produtosVendidosHoje =
                vendaRepository.buscarProdutosVendidosHoje();

        BigDecimal receitaMensal =
                vendaRepository.buscarReceitaMensal();

        Long fiadosQuitadosHoje =
                pagamentoFiadoRepository.buscarFiadosQuitadosHoje();

        List<VendaPorDiaDTO> vendasUltimosDias =
                vendaRepository
                        .buscarVendasUltimosDias()
                        .stream()
                        .map(obj -> new VendaPorDiaDTO(

                                String.valueOf(obj[0]),

                                (BigDecimal) obj[1]
                        ))
                        .toList();

        return new DashboardResponseDTO(

                valorVendasHoje,

                quantidadeVendasHoje,

                fiadosQuitadosHoje,

                produtosVendidosHoje,

                receitaMensal,

                vendasUltimosDias,

                itemVendaRepository
                        .buscarProdutosMaisVendidosHoje(),

                vendaRepository
                        .buscarPagamentosPorTipo()
        );
    }
}