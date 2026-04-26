package com.fresquim.paofresquim_backend.dtos;

import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;

import java.util.List;

public record VendaRequestDTO(
     List<ProdutoRequestDTO> products,
     Long idCliente,
     Long idFuncionario
){}

