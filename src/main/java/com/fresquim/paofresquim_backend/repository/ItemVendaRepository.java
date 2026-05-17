package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.dtos.HistoricoVendaDTO;
import com.fresquim.paofresquim_backend.dtos.ProdutoMaisVendidoDTO;
import com.fresquim.paofresquim_backend.entity.ItemVenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemVendaRepository
        extends JpaRepository<ItemVenda, Long> {

    List<ItemVenda> findByVendaId(Long id);

    @Query("""
        SELECT new com.fresquim.paofresquim_backend.dtos.HistoricoVendaDTO(

            v.id,

            v.dataVenda,

            COALESCE(c.nome, 'Consumidor Final'),

            SUM(iv.quantidade),

            v.valorTotal
        )

        FROM ItemVenda iv

        JOIN iv.venda v

        LEFT JOIN v.cliente c

        WHERE v.dataVenda >= :dataInicial
        AND v.dataVenda <= :dataFinal

        GROUP BY
            v.id,
            v.dataVenda,
            c.nome,
            v.valorTotal

        ORDER BY v.dataVenda DESC
    """)
    List<HistoricoVendaDTO> buscarHistorico(

            @Param("dataInicial")
            LocalDateTime dataInicial,

            @Param("dataFinal")
            LocalDateTime dataFinal
    );

    @Query("""
        SELECT COALESCE(SUM(iv.quantidade), 0)

        FROM ItemVenda iv

        JOIN iv.venda v

        JOIN iv.produto p

        WHERE p.nome ILIKE :filtroProduto

        AND v.dataVenda >= :dataInicial

        AND v.dataVenda <= :dataFinal
    """)
    Long buscarTotalQuantidade(

            @Param("filtroProduto")
            String filtroProduto,

            @Param("dataInicial")
            LocalDateTime dataInicial,

            @Param("dataFinal")
            LocalDateTime dataFinal
    );

    @Query("""
        SELECT new com.fresquim.paofresquim_backend.dtos.ProdutoMaisVendidoDTO(

            p.nome,

            SUM(iv.quantidade)
        )

        FROM ItemVenda iv

        JOIN iv.produto p

        JOIN iv.venda v

        WHERE p.nome ILIKE :filtroProduto

        AND v.dataVenda >= :dataInicial

        AND v.dataVenda <= :dataFinal

        GROUP BY p.nome

        ORDER BY SUM(iv.quantidade) DESC,
                 p.nome ASC
    """)
    List<ProdutoMaisVendidoDTO>
    buscarProdutosMaisVendidos(

            @Param("filtroProduto")
            String filtroProduto,

            @Param("dataInicial")
            LocalDateTime dataInicial,

            @Param("dataFinal")
            LocalDateTime dataFinal
    );

    boolean existsByProdutoId(Long produtoId);

    @Query("""
    SELECT new com.fresquim.paofresquim_backend.dtos.ProdutoMaisVendidoDTO(

        p.nome,

        SUM(iv.quantidade)
    )

    FROM ItemVenda iv

    JOIN iv.produto p

    JOIN iv.venda v

    WHERE CAST(v.dataVenda AS LocalDate)
    = CURRENT_DATE

    GROUP BY p.nome

    ORDER BY SUM(iv.quantidade) DESC
""")
    List<ProdutoMaisVendidoDTO>
    buscarProdutosMaisVendidosHoje();
}