package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.PagamentoFiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoFiadoRepository extends JpaRepository<PagamentoFiado, Long> {

    List<PagamentoFiado> findByClienteIdClienteAndQuitadoFalse(Long idCliente);
}
