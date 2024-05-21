package com.tp2.produtos.controller;

import com.tp2.produtos.model.Produto;
import com.tp2.produtos.repository.ClienteRepository;
import com.tp2.produtos.repository.ProdutoRepository;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/cliente/{clienteId}")
    public void cadastrarProduto(@PathVariable Long clienteId, @RequestBody Produto produto) {
        clienteRepository.findById(clienteId).map(cliente ->
        {
            produto.setCliente(cliente);
            return produtoRepository.save(produto);

        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado com id "+ clienteId));
    }

    @GetMapping
    public List<Produto> getAllProdutos(){
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado com id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id){
        return produtoRepository.findById(id).map(produto -> {
            produtoRepository.delete(produto);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado com id " + id));

    }
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoUpdate)
    {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoUpdate.getNome());
            produto.setDescricao(produtoUpdate.getDescricao());
            produto.setCategoria(produtoUpdate.getCategoria());
            produto.setValor(produtoUpdate.getValor());
            return produtoRepository.save(produto);
        }).orElseThrow(()-> new RuntimeException("Produto não encontrado com id"+ id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> getAllProdutosCliente(@PathVariable Long clienteId)
    {
        List<Produto> produtos = produtoRepository.findByClienteId(clienteId);
        if(produtos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtos);
    }
}
