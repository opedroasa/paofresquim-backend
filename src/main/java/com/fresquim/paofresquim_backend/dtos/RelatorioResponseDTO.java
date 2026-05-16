package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioResponseDTO(
        BigDecimal totalVendas,
        Long totalQuantidade,
        BigDecimal totalFiado,
        BigDecimal saldoAtual,
        List<ProdutoMaisVendidoDTO> produtosMaisVendidos,
        List<HistoricoVendaDTO> vendas,
        List<ClienteFiadoDTO> clientesFiado
) {
}
