package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @Column(name = "quantidade_atual", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidadeAtual;

    @Column(name = "estoque_minimo", nullable = false, precision = 10, scale = 3)
    private BigDecimal estoqueMinimo;

    public Estoque() {
    }

    public Estoque(Long id, Produto produto, BigDecimal quantidadeAtual, BigDecimal estoqueMinimo) {
        this.id = id;
        this.produto = produto;
        this.quantidadeAtual = quantidadeAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    public Long getId() { return id ;}

    public Produto getProduto() { return produto; }

    public BigDecimal getQuantidadeAtual() { return quantidadeAtual; }

    public BigDecimal getEstoqueMinimo() { return estoqueMinimo; }

    public void setId(Long id) { this.id = id; }

    public void setProduto(Produto produto) { this.produto = produto; }

    public void setQuantidadeAtual(BigDecimal quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }

    public void setEstoqueMinimo(BigDecimal estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public boolean isAbaixoDoMinimo() {
        return quantidadeAtual != null
                && estoqueMinimo != null
                && quantidadeAtual.compareTo(estoqueMinimo) < 0;
    }
}
