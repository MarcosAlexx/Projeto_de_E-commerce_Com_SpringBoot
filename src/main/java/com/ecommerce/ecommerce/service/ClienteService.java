package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Cliente;
import com.ecommerce.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(String cpf, String nome, String email) {
        Cliente cliente = new Cliente(cpf, nome, email);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Cliente removerCliente(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado");
        }

        Cliente cliente = clienteOpt.get();
        clienteRepository.deleteById(id);

        return cliente;
    }
}