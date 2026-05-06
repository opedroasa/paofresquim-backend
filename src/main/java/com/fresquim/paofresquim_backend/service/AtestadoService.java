package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.AtestadoResponseDTO;
import com.fresquim.paofresquim_backend.dtos.CriarAtestadoRequestDTO;
import com.fresquim.paofresquim_backend.entity.Atestado;
import com.fresquim.paofresquim_backend.entity.Funcionario;
import com.fresquim.paofresquim_backend.repository.AtestadoRepository;
import com.fresquim.paofresquim_backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtestadoService {

    private final AtestadoRepository atestadoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public AtestadoService(
            AtestadoRepository atestadoRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.atestadoRepository = atestadoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public AtestadoResponseDTO criar(CriarAtestadoRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (dto.descricao() == null || dto.descricao().isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }

        if (dto.dataInicio() == null || dto.dataFim() == null) {
            throw new IllegalArgumentException("Data de início e data de fim são obrigatórias");
        }

        if (dto.dataFim().isBefore(dto.dataInicio())) {
            throw new IllegalArgumentException("Data de fim não pode ser anterior à data de início");
        }

        Atestado atestado = new Atestado();
        atestado.setFuncionario(funcionario);
        atestado.setDescricao(dto.descricao());
        atestado.setDataInicio(dto.dataInicio());
        atestado.setDataFim(dto.dataFim());

        return toResponseDTO(atestadoRepository.save(atestado));
    }

    public List<AtestadoResponseDTO> listar() {
        return atestadoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AtestadoResponseDTO> listarPorFuncionario(Integer funcionarioId) {
        return atestadoRepository.findByFuncionarioIdFuncionario(funcionarioId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private AtestadoResponseDTO toResponseDTO(Atestado atestado) {
        return new AtestadoResponseDTO(
                atestado.getId(),
                atestado.getFuncionario().getIdFuncionario(),
                atestado.getFuncionario().getNome(),
                atestado.getDescricao(),
                atestado.getDataInicio(),
                atestado.getDataFim()
        );
    }
}