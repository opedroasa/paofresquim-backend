package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);
}