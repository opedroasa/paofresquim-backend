package com.fresquim.paofresquim_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatbot_atendimento")
public class ChatbotAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento")
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "status", length = 50)
    private String status = "aberto";

    @Column(name = "transbordado_humano")
    private Boolean transbordadoHumano = false;

    @Column(name = "resolvido_primeiro_contato")
    private Boolean resolvidoPrimeiroContato = false;

    @Column(name = "nota_csat")
    private Short notaCsat;

    @Column(name = "tempo_total_segundos")
    private Integer tempoTotalSegundos;

    @OneToMany(
            mappedBy = "atendimento",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatbotMensagem> mensagens = new ArrayList<>();

    public ChatbotAtendimento() {
    }
}