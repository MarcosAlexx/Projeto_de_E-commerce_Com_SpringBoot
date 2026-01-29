package com.ecommerce.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;


@Data
@Entity
@Table(name = "tb_produtos")
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double preco;
    @Getter
    private int estoque;

    public Produto(String nome, double preco, int estoque){
        this.nome=nome;
        this.preco=preco;
        this.estoque=estoque;
    }

    public Produto() {}

}
