package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Venda;
import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda,Long> {

    @Query("""
    SELECT COALESCE(SUM(v.valorTotal), 0)
    FROM Venda v
    WHERE
        v.dataVenda >= COALESCE(:dataInicial, v.dataVenda)
        AND v.dataVenda <= COALESCE(:dataFinal, v.dataVenda)
""")
    BigDecimal buscarTotalVendas(
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal
    );

    List<Venda> findByTipoPagamentoAndStatusPagamento(
            TipoPagamento tipoPagamento,
            Boolean statusPagamento
    );

    @Query("""
    SELECT v
    FROM Venda v
    LEFT JOIN FETCH v.cliente
    WHERE v.tipoPagamento = :tipoPagamento
    AND v.statusPagamento = :statusPagamento
""")
    List<Venda> buscarFiadosPendentes(
            @Param("tipoPagamento")
            TipoPagamento tipoPagamento,

            @Param("statusPagamento")
            boolean statusPagamento
    );
}