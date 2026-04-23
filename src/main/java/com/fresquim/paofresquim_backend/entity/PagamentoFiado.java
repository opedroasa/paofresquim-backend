package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento_fiado")
public class PagamentoFiado {

    // Implementar um campo para limite fiado do cliente e consultas sobre historico financeiro do cliente

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal valorDevido;

    @Column(nullable = false)
    private Boolean quitado;

    @Column
    private LocalDateTime dataPagamento;

    public PagamentoFiado() {}

    public PagamentoFiado(Venda venda, Cliente cliente, BigDecimal valorDevido) {
        this.venda = venda;
        this.cliente = cliente;
        this.valorDevido = valorDevido;
        this.quitado = false;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorDevido() {
        return valorDevido;
    }

    public void setValorDevido(BigDecimal valorDevido) {
        this.valorDevido = valorDevido;
    }

    public Boolean getQuitado() {
        return quitado;
    }

    public void setQuitado(Boolean quitado) {
        this.quitado = quitado;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Long getId() {
        return id;
    }
}
