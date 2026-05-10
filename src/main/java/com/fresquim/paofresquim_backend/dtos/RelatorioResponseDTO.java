package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioResponseDTO(
        BigDecimal totalVendas,
        Long totalQuantidade,
        BigDecimal totalFiado,
        BigDecimal saldoAtual,
        ProdutoMaisVendidoDTO produtoMaisVendido,
        List<HistoricoVendaDTO> vendas,
        List<ClienteFiadoDTO> clientesFiado
) {
}
