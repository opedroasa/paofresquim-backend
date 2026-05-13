package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chatbot_mensagem")
public class ChatbotMensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_atendimento",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_chatbot_mensagem_atendimento")
    )
    private ChatbotAtendimento atendimento;

    @Column(name = "remetente", nullable = false, length = 20)
    private String remetente;

    @Column(name = "mensagem", nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "acao", length = 100)
    private String acao;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    public ChatbotMensagem() {
    }
}