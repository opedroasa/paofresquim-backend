package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

import java.util.List;

public record DashboardResponseDTO(

        BigDecimal valorVendasHoje,

        Long quantidadeVendasHoje,

        Long fiadosQuitadosHoje,

        Long produtosVendidosHoje,

        BigDecimal receitaMensal,

        List<VendaPorDiaDTO> vendasUltimosDias,

        List<ProdutoMaisVendidoDTO> produtosMaisVendidos,

        List<PagamentoTipoDTO> pagamentosPorTipo

) {
}