package com.fresquim.paofresquim_backend.repository;

import com.fresquim.paofresquim_backend.entity.Atestado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtestadoRepository extends JpaRepository<Atestado, Integer> {

    List<Atestado> findByFuncionarioId(Integer funcionarioId);
}