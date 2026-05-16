package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;
import java.util.List;

public record VendaResponseDTO(

        Long id,

        List<ProdutoVendaResponseDTO> produtos,

        Long idCliente,

        String nomeCliente,

        Long idFuncionario,

        BigDecimal subTotal,

        boolean statusPagamento

) {
}