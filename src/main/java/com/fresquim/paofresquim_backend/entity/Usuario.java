package com.fresquim.paofresquim_backend.entity;

import com.fresquim.paofresquim_backend.entity.enums.CargoUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoUsuario cargoUsuario;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String login, String senha, CargoUsuario cargoUsuario) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cargoUsuario = cargoUsuario;
    }

    public static Usuario criar(String nome, String login, String senha, CargoUsuario cargoUsuario) {
        return new Usuario(null, nome, login, senha, cargoUsuario);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public CargoUsuario getCargoUsuario() {
        return cargoUsuario;
    }

    public void setCargoUsuario(CargoUsuario cargoUsuario) {
        this.cargoUsuario = cargoUsuario;
    }

    public String getRole() {
        return "ROLE_" + this.cargoUsuario.name();
    }
}
