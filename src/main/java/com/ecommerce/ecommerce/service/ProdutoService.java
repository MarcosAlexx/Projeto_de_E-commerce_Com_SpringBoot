package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Produto;
import com.ecommerce.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    Produto produto;

    public Produto cadastraNovoProduto(String nome, double preco, int estoque) {
        Produto novoProduto = new Produto(nome, preco, estoque);
        produtoRepository.save(novoProduto);
        return novoProduto;
    }

    public void diminuiEstoque(int produtoId, int quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }

        produto.setEstoque(produto.getEstoque() - quantidade);
        produtoRepository.save(produto);
    }


    public List<Produto> listarProdutos(){
        return  produtoRepository.findAll();
    }

    public Optional<Produto> buscaProdutoId(int id){
        return produtoRepository.findById(id);
    }

}
