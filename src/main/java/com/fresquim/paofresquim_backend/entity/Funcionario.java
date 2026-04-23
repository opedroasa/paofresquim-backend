package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Integer idFuncionario;

    private String nome;
    private String telefone;
    private String endereco;
    private String senha;

    @Column(unique = true)
    private String cpf;

    @Column(name = "telefone_emergencia")
    private String telefoneEmergencia;

    public Funcionario() {}

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public String getSenha() {return senha;}

    public void setSenha(String senha) {this.senha = senha;}

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }
}