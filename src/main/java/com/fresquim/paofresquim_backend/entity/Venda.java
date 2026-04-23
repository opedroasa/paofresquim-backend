package com.fresquim.paofresquim_backend.entity;

import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda")
    private Long id;

    @Column(nullable = false, name = "data_venda")
    private LocalDateTime dataVenda;
    @Column(nullable = false, name = "valor_total")
    private BigDecimal valorTotal;
    @Column(nullable = false, name = "tipo_pagamento")
    private TipoPagamento tipoPagamento;
    @Column(name = "status_pagamento")
    private Boolean statusPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funcionario funcionario;

    public Venda() {
    }

    public Venda(
            LocalDateTime dataVenda,
            BigDecimal valorTotal,
            Cliente cliente,
            Funcionario funcionario
    ) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public Venda(
            LocalDateTime dataVenda,
            BigDecimal valorTotal,
            TipoPagamento tipoPagamento,
            Boolean statusPagamento,
            Cliente cliente,
            Funcionario funcionario
    ) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.tipoPagamento = tipoPagamento;
        this.statusPagamento = statusPagamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public Long getId() { return id; }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalDateTime getDataVenda() {
        return this.dataVenda;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public TipoPagamento getTipoPagamento() {
        return this.tipoPagamento;
    }

    public void setStatusPagamento(Boolean statusPagamento) {this.statusPagamento = statusPagamento;}

    public Boolean getStatusPagamento() {return statusPagamento;}

    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Cliente getCliente() {return cliente;}

    public void setFuncionario(Funcionario funcionario) {this.funcionario = funcionario;}

    public Funcionario getFuncionario() {return funcionario;}
}
