package com.fresquim.paofresquim_backend.entity;

import com.fresquim.paofresquim_backend.entity.enums.Unidade;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "unidade_medida", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unidade unidadeMedida;

    @Column(name = "codigo_barras", nullable = false)
    private String codigoBarras;

    @Column(nullable = false)
    private Boolean favorito = false;

    public Produto() {}

    public Produto(String nome, BigDecimal preco, Unidade unidadeMedida, String codigoBarras, Boolean favorito) {
        this.nome = nome;
        this.preco = preco;
        this.unidadeMedida = unidadeMedida;
        this.codigoBarras = codigoBarras;
        this.favorito = favorito != null ? favorito : false;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Unidade getUnidadeMedida() {
        return unidadeMedida;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setUnidadeMedida(Unidade unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Boolean getFavorito() {return favorito;}

    public void setFavorito(Boolean favorito) {this.favorito = favorito;}
}
