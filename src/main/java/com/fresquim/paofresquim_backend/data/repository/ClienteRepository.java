package com.fresquim.paofresquim_backend.data.repository;

import com.fresquim.paofresquim_backend.data.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}