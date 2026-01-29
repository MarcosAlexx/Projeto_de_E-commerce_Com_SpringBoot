package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.CRIADO;

    @CreationTimestamp
    private LocalDate data;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensDoPedido = new ArrayList<>();

    public Pedido() {}

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.statusPedido = StatusPedido.CRIADO;
    }

    public double calculaTotalPedidos() {
        double total = 0;
        for (ItemPedido item : itensDoPedido) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void mudaStatus() {
        switch (statusPedido) {
            case CRIADO:
                statusPedido = StatusPedido.PAGO;
                break;
            case PAGO:
                statusPedido = StatusPedido.ENVIADO;
                break;
            case ENVIADO:
                throw new IllegalStateException("O pedido já foi enviado e não pode ser alterado.");
            case CANCELADO:
                throw new IllegalStateException("O pedido foi cancelado e não pode ser alterado.");
        }
    }
}