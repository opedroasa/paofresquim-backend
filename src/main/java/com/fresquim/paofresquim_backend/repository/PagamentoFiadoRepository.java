package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.dtos.ClienteFiadoDTO;
import com.fresquim.paofresquim_backend.entity.PagamentoFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PagamentoFiadoRepository extends JpaRepository<PagamentoFiado, Long> {

    List<PagamentoFiado> findByClienteIdClienteAndQuitadoFalse(Long idCliente);

    @Query("""
        SELECT COALESCE(SUM(pf.valorDevido), 0)
        FROM PagamentoFiado pf
        WHERE pf.quitado = false
    """)
    BigDecimal buscarTotalFiado();

    @Query("""
        SELECT new com.fresquim.paofresquim_backend.dtos.ClienteFiadoDTO(
            c.nome,
            pf.valorDevido
        )
        FROM PagamentoFiado pf
        JOIN pf.cliente c
        WHERE pf.quitado = false
    """)
    List<ClienteFiadoDTO> buscarClientesFiado();
}
