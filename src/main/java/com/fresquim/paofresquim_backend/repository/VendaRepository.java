package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda,Long> {

}
