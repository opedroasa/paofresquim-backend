package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    Optional<Produto> findFirstByNomeContainingIgnoreCase(String nome);
}
