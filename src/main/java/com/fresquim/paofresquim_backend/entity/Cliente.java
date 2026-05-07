package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(unique = true)
    private String email;

    @Column(name = "status_credito")
    private String statusCredito;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public String getStatusCredito() {
        return statusCredito;
    }

    public void setStatusCredito(String statusCredito) {
        this.statusCredito = statusCredito;
    }
}