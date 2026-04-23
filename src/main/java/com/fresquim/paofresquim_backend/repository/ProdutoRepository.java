package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
