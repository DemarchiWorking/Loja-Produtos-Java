package com.tp2.produtos.repository;

import com.tp2.produtos.model.Cliente;
import com.tp2.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByClienteId(Long clienteId);
}
