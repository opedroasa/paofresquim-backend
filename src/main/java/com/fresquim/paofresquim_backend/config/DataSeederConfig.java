package com.fresquim.paofresquim_backend.config;

import com.fresquim.paofresquim_backend.entity.*;
import com.fresquim.paofresquim_backend.entity.enums.TipoPagamento;
import com.fresquim.paofresquim_backend.entity.enums.Unidade;
import com.fresquim.paofresquim_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataSeederConfig {

    @Bean
    CommandLineRunner seedDatabase(
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            VendaRepository vendaRepository,
            ItemVendaRepository itemVendaRepository,
            PagamentoFiadoRepository pagamentoFiadoRepository
    ) {

        return args -> {

            if (produtoRepository.count() > 0) {
                return;
            }

            // =========================
            // CLIENTES
            // =========================

            Cliente cliente1 = new Cliente();
            cliente1.setNome("João Silva");
            cliente1.setTelefone("34999999999");
            cliente1.setEndereco("Rua A");
            cliente1.setEmail("joao@email.com");
            cliente1.setStatusCredito("BOM");

            Cliente cliente2 = new Cliente();
            cliente2.setNome("Maria Souza");
            cliente2.setTelefone("34988888888");
            cliente2.setEndereco("Rua B");
            cliente2.setEmail("maria@email.com");
            cliente2.setStatusCredito("REGULAR");

            Cliente cliente3 = new Cliente();
            cliente3.setNome("Carlos Lima");
            cliente3.setTelefone("34977777777");
            cliente3.setEndereco("Rua C");
            cliente3.setEmail("carlos@email.com");
            cliente3.setStatusCredito("RUIM");

            clienteRepository.saveAll(
                    List.of(cliente1, cliente2, cliente3)
            );

            // =========================
            // PRODUTOS
            // =========================

            Produto pao = new Produto(
                    "Pão Francês",
                    new BigDecimal("1.50"),
                    Unidade.KG,
                    "111111"
            );

            Produto bolo = new Produto(
                    "Bolo de Chocolate",
                    new BigDecimal("25.00"),
                    Unidade.KG,
                    "222222"
            );

            Produto cafe = new Produto(
                    "Café",
                    new BigDecimal("5.00"),
                    Unidade.G,
                    "333333"
            );

            Produto leite = new Produto(
                    "Leite",
                    new BigDecimal("6.50"),
                    Unidade.L,
                    "444444"
            );

            produtoRepository.saveAll(
                    List.of(pao, bolo, cafe, leite)
            );

            // =========================
            // VENDAS
            // =========================

            Venda venda1 = new Venda();
            venda1.setCliente(cliente1);
            venda1.setDataVenda(LocalDateTime.now().minusDays(5));
            venda1.setValorTotal(new BigDecimal("40.00"));
            venda1.setTipoPagamento(TipoPagamento.PIX);
            venda1.setStatusPagamento(true);

            Venda venda2 = new Venda();
            venda2.setCliente(cliente2);
            venda2.setDataVenda(LocalDateTime.now().minusDays(3));
            venda2.setValorTotal(new BigDecimal("65.00"));
            venda2.setTipoPagamento(TipoPagamento.FIADO);
            venda2.setStatusPagamento(false);

            Venda venda3 = new Venda();
            venda3.setCliente(cliente1);
            venda3.setDataVenda(LocalDateTime.now().minusDays(1));
            venda3.setValorTotal(new BigDecimal("32.50"));
            venda3.setTipoPagamento(TipoPagamento.PIX);
            venda3.setStatusPagamento(true);

            vendaRepository.saveAll(
                    List.of(venda1, venda2, venda3)
            );

            // =========================
            // ITENS VENDA
            // =========================

            ItemVenda item1 = new ItemVenda(
                    10L,
                    new BigDecimal("1.50"),
                    new BigDecimal("15.00"),
                    venda1,
                    pao
            );

            ItemVenda item2 = new ItemVenda(
                    1L,
                    new BigDecimal("25.00"),
                    new BigDecimal("25.00"),
                    venda1,
                    bolo
            );

            ItemVenda item3 = new ItemVenda(
                    5L,
                    new BigDecimal("5.00"),
                    new BigDecimal("25.00"),
                    venda2,
                    cafe
            );

            ItemVenda item4 = new ItemVenda(
                    6L,
                    new BigDecimal("6.50"),
                    new BigDecimal("39.00"),
                    venda2,
                    leite
            );

            ItemVenda item5 = new ItemVenda(
                    5L,
                    new BigDecimal("1.50"),
                    new BigDecimal("7.50"),
                    venda3,
                    pao
            );

            ItemVenda item6 = new ItemVenda(
                    5L,
                    new BigDecimal("5.00"),
                    new BigDecimal("25.00"),
                    venda3,
                    cafe
            );

            itemVendaRepository.saveAll(
                    List.of(item1, item2, item3, item4, item5, item6)
            );

            // =========================
            // PAGAMENTOS FIADO
            // =========================

            PagamentoFiado fiado1 = new PagamentoFiado();
            fiado1.setCliente(cliente2);
            fiado1.setVenda(venda2);
            fiado1.setValorDevido(new BigDecimal("45.00"));
            fiado1.setQuitado(false);

            PagamentoFiado fiado2 = new PagamentoFiado();
            fiado2.setCliente(cliente3);
            fiado2.setVenda(venda1);
            fiado2.setValorDevido(new BigDecimal("80.00"));
            fiado2.setQuitado(false);

            PagamentoFiado fiado3 = new PagamentoFiado();
            fiado3.setCliente(cliente1);
            fiado3.setVenda(venda3);
            fiado3.setValorDevido(new BigDecimal("20.00"));
            fiado3.setQuitado(true);

            pagamentoFiadoRepository.saveAll(
                    List.of(fiado1, fiado2, fiado3)
            );

            System.out.println("Banco populado com sucesso.");
        };
    }
}