package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table
@Entity(name = "item_venda")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_item")
    private Long id;
    private Long quantidade;
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;
    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;


    public ItemVenda(){}
    public ItemVenda(
            Long quantidade,
            BigDecimal precoUnitario,
            BigDecimal subTotal,
            Venda venda,
            Produto produto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subTotal = subTotal;
        this.venda = venda;
        this.produto = produto;
    }

    public Long getId() { return id; }

    public void setQuantidade(Long quantidade) {this.quantidade = quantidade;}

    public Long getQuantidade() {return quantidade;}

    public void setPrecoUnitario(BigDecimal precoUnitario) {this.precoUnitario = precoUnitario;}

    public BigDecimal getPrecoUnitario() {return precoUnitario;}

    public void setSubTotal(BigDecimal subTotal) {this.subTotal = subTotal;}

    public BigDecimal getSubTotal() {return subTotal;}

    public void setVenda(Venda venda) {this.venda = venda;}

    public Venda getVenda() {return venda;}

    public void setProduto(Produto produto) {this.produto = produto;}

    public Produto getProduto() {return produto;}
}
