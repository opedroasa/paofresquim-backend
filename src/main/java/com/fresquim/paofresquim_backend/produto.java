package br.com.paofresquim.produto;

import br.com.paofresquim.estoque.Estoque;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(
        name = "produto",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_produto_codigo_barras", columnNames = "codigo_barras")
        }
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "unidade_medida", nullable = false, length = 10)
    private String unidadeMedida;

    @Column(name = "codigo_barras", nullable = false, length = 50)
    private String codigoBarras;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Estoque estoque;

    public Produto() {
    }

    public Produto(Long id, String nome, BigDecimal preco, String unidadeMedida, String codigoBarras) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.unidadeMedida = unidadeMedida;
        this.codigoBarras = codigoBarras;
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

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public Estoque getEstoque() {
        return estoque;
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

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
        if (estoque != null) {
            estoque.setProduto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}