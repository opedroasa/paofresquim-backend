package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.AtestadoDTO;
import com.fresquim.paofresquim_backend.entity.Atestado;
import com.fresquim.paofresquim_backend.entity.Funcionario;
import com.fresquim.paofresquim_backend.repository.AtestadoRepository;
import com.fresquim.paofresquim_backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtestadoService {

    private final AtestadoRepository repository;
    private final FuncionarioRepository funcionarioRepository;

    public AtestadoService(AtestadoRepository repository,
                           FuncionarioRepository funcionarioRepository) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public AtestadoDTO criar(AtestadoDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Atestado atestado = new Atestado();
        atestado.setFuncionario(funcionario);
        atestado.setDescricao(dto.getDescricao());
        atestado.setDataInicio(dto.getDataInicio());
        atestado.setDataFim(dto.getDataFim());

        return toDTO(repository.save(atestado));
    }

    public List<AtestadoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AtestadoDTO> listarPorFuncionario(Integer funcionarioId) {
        return repository.findByFuncionarioId(funcionarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AtestadoDTO toDTO(Atestado atestado) {
        AtestadoDTO dto = new AtestadoDTO();
        dto.setId(atestado.getId());
        dto.setFuncionarioId(atestado.getFuncionario().getId());
        dto.setNomeFuncionario(atestado.getFuncionario().getNome());
        dto.setDescricao(atestado.getDescricao());
        dto.setDataInicio(atestado.getDataInicio());
        dto.setDataFim(atestado.getDataFim());
        return dto;
    }
}