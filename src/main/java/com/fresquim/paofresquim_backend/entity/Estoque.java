package com.fresquim.paofresquim_backend.entity;

import com.fresquim.paofresquim_backend.entity.Produto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_produto",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_estoque_produto")
    )
    private Produto produto;

    @Column(name = "quantidade_atual", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantidadeAtual;

    @Column(name = "estoque_minimo", nullable = false, precision = 10, scale = 3)
    private BigDecimal estoqueMinimo;

    @Column(name = "ultima_atualizacao", nullable = false)
    private LocalDateTime ultimaAtualizacao;

    public Estoque() {
    }

    public Estoque(Long id, Produto produto, BigDecimal quantidadeAtual, BigDecimal estoqueMinimo) {
        this.id = id;
        this.produto = produto;
        this.quantidadeAtual = quantidadeAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    @PrePersist
    @PreUpdate
    public void atualizarData() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidadeAtual(BigDecimal quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public void setEstoqueMinimo(BigDecimal estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public boolean isAbaixoDoMinimo() {
        return quantidadeAtual != null
                && estoqueMinimo != null
                && quantidadeAtual.compareTo(estoqueMinimo) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estoque estoque)) return false;
        return Objects.equals(id, estoque.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
