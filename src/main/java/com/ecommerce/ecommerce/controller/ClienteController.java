package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Cliente;
import com.ecommerce.ecommerce.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.cadastrarCliente(
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PostMapping("/deletar/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id){
        clienteService.removerCliente(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente Removiso com sucesso");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        return clienteService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}