package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository < Produto, Long> {
    Optional<Produto> findById(long id);
}
