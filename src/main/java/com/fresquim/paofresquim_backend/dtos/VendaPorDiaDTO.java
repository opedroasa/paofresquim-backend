package com.fresquim.paofresquim_backend.dtos;

import java.math.BigDecimal;

public record VendaPorDiaDTO(

        String dia,

        BigDecimal valor

) {
}