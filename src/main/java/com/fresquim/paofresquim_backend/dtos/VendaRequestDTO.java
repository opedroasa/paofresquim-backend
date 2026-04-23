package com.fresquim.paofresquim_backend.dtos;

import java.util.List;

public record VendaRequestDTO(
     List<ProdutoRequestDTO> products,
     Long idCliente,
     Long idFuncionario
){}

