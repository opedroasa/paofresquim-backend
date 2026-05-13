package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimento_chatbot")
public class AtendimentoChatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento")
    private Long id;

    @Column(name = "id_conversa", nullable = false, unique = true)
    private String idConversa;

    @Column(name = "id_usuario")
    private String idUsuario;

    @Column(name = "inicio_atendimento", nullable = false)
    private LocalDateTime inicioAtendimento;

    @Column(name = "fim_atendimento")
    private LocalDateTime fimAtendimento;

    @Column(name = "tempo_atendimento_segundos")
    private Integer tempoAtendimentoSegundos;

    @Column(name = "houve_transbordo", nullable = false)
    private Boolean houveTransbordo;

    @Column(name = "resolvido_primeiro_contato", nullable = false)
    private Boolean resolvidoPrimeiroContato;

    @Column(name = "csat")
    private Integer csat;

    @Column(name = "feedback_cliente", columnDefinition = "TEXT")
    private String feedbackCliente;

    public AtendimentoChatbot() {
    }
}