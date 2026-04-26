package com.fresquim.paofresquim_backend.service;

import com.fresquim.paofresquim_backend.dtos.PagamentoFiadoRequestDTO;
import com.fresquim.paofresquim_backend.entity.Cliente;
import com.fresquim.paofresquim_backend.entity.PagamentoFiado;
import com.fresquim.paofresquim_backend.entity.Venda;
import com.fresquim.paofresquim_backend.repository.ClienteRepository;
import com.fresquim.paofresquim_backend.repository.PagamentoFiadoRepository;
import com.fresquim.paofresquim_backend.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PagamentoFiadoService {

    @Autowired
    private PagamentoFiadoRepository repository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public void gerarPagamentoFiado(PagamentoFiadoRequestDTO dto) {

        Venda venda = vendaRepository.findById(dto.idVenda())
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        Cliente cliente = clienteRepository.findById(dto.idCliente().intValue())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (cliente == null) {
            throw new IllegalArgumentException("Venda fiado precisa de cliente");
        }

        PagamentoFiado fiado = new PagamentoFiado(
                venda,
                cliente,
                dto.valorDevido()
        );

        repository.save(fiado);
    }

    public BigDecimal consultarSaldoEmAbertoPorCliente(Long idCliente) {

        List<PagamentoFiado> pendentes =
                repository.findByClienteIdClienteAndQuitadoFalse(idCliente);

        return pendentes.stream()
                .map(PagamentoFiado::getValorDevido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
