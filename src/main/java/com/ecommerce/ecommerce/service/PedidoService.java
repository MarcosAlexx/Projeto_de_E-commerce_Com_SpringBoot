package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Cliente;
import com.ecommerce.ecommerce.model.ItemPedido;
import com.ecommerce.ecommerce.model.Pedido;
import com.ecommerce.ecommerce.model.Produto;
import com.ecommerce.ecommerce.model.StatusPedido;
import com.ecommerce.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    public Pedido cadastrarNovoPedido(long clienteId) {
        Cliente cliente = clienteService.buscarClientePorId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido(cliente);
        return pedidoRepository.save(pedido);
    }

    public Pedido adicionarItemAoPedido(long pedidoId, int produtoId, int quantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatusPedido() == StatusPedido.CANCELADO ||
                pedido.getStatusPedido() == StatusPedido.ENVIADO) {
            throw new IllegalStateException("Não é possível adicionar itens quando o pedido está " + pedido.getStatusPedido());
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        Produto produto = produtoService.buscaProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException("Produto indisponível no momento, estoque insuficiente!");
        }

        produtoService.diminuiEstoque(produtoId, quantidade);

        ItemPedido item = new ItemPedido(produto, quantidade);
        item.setPedido(pedido);
        pedido.getItensDoPedido().add(item);

        if (pedido.getStatusPedido() == StatusPedido.CRIADO) {
            pedido.mudaStatus();
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido mudarStatusPedido(long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.mudaStatus();
        return pedidoRepository.save(pedido);
    }

    public Double calcularTotalPedido(long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        return pedido.calculaTotalPedidos();
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> listarPedidosDoCliente(long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }
}