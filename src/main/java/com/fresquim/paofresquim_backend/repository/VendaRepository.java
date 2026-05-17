package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.dtos.PagamentoTipoDTO;
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
public interface VendaRepository extends JpaRepository<Venda, Long> {

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

    @Query("""
        SELECT COALESCE(SUM(v.valorTotal), 0)
        FROM Venda v
        WHERE CAST(v.dataVenda AS LocalDate) = CURRENT_DATE
    """)
    BigDecimal buscarValorVendasHoje();

    @Query("""
        SELECT COUNT(v)
        FROM Venda v
        WHERE CAST(v.dataVenda AS LocalDate) = CURRENT_DATE
    """)
    Long buscarQuantidadeVendasHoje();

    @Query("""
        SELECT COALESCE(SUM(iv.quantidade), 0)
        FROM ItemVenda iv
        WHERE CAST(iv.venda.dataVenda AS LocalDate) = CURRENT_DATE
    """)
    Long buscarProdutosVendidosHoje();

    @Query("""
        SELECT COALESCE(SUM(v.valorTotal), 0)
        FROM Venda v
        WHERE YEAR(v.dataVenda) = YEAR(CURRENT_DATE)
        AND MONTH(v.dataVenda) = MONTH(CURRENT_DATE)
    """)
    BigDecimal buscarReceitaMensal();

    @Query("""
        SELECT
            FUNCTION('TO_CHAR', v.dataVenda, 'DD/MM'),
            SUM(v.valorTotal)
        FROM Venda v
        WHERE v.dataVenda >= CURRENT_DATE - 7 DAY
        GROUP BY FUNCTION('TO_CHAR', v.dataVenda, 'DD/MM')
        ORDER BY FUNCTION('TO_CHAR', v.dataVenda, 'DD/MM')
    """)
    List<Object[]> buscarVendasUltimosDias();

    @Query("""
        SELECT new com.fresquim.paofresquim_backend.dtos.PagamentoTipoDTO(

            CAST(v.tipoPagamento AS string),

            COUNT(v)
        )

        FROM Venda v

        GROUP BY v.tipoPagamento
    """)
    List<PagamentoTipoDTO> buscarPagamentosPorTipo();
}