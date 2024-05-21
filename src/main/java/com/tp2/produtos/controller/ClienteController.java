package com.tp2.produtos.controller;

import com.tp2.produtos.model.Cliente;
import com.tp2.produtos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> getAllClientes()
    {
        return clienteRepository.findAll();
    }
    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id)
    {
         return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado "+ id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id)
    {
            Cliente cliente = clienteRepository.findById(id).orElseThrow(()-> new RuntimeException("Cliente não encontrado"+ id));
            clienteRepository.delete(cliente);
            return ResponseEntity.ok().build();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente)
    {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente)
    {
        Cliente cli = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cli.setNome(cliente.getNome());
        cli.setTelefone(cliente.getTelefone());

        return clienteRepository.save(cli);
    }
}
