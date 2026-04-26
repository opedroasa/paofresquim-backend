package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_ponto")


public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public RegistroPonto() {}

    public RegistroPonto(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataHora = LocalDateTime.now();
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        this.nome = nome;
    }

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        this.cpf = cpf;
    }

    public LocalDateTime getDataHora() {return dataHora;}

    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}
}

