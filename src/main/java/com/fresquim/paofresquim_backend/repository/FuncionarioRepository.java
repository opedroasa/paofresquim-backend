package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}