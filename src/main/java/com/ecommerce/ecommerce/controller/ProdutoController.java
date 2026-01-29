package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Cliente;
import com.ecommerce.ecommerce.model.Produto;
import com.ecommerce.ecommerce.repository.ClienteRepository;
import com.ecommerce.ecommerce.repository.ProdutoRepository;
import com.ecommerce.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/Cadastrar")
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto){
        Produto novoProduto = produtoService.cadastraNovoProduto(
                produto.getNome(),
                produto.getEstoque(),
                produto.getEstoque());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutosCriados() {
        var produtos = produtoService.listarProdutos();
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }


}
