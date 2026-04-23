package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda,Long> {

    List<ItemVenda> findByVendaId(Long id);


}
