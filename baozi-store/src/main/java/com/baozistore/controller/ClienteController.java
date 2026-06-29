package com.baozistore.controller;

import com.baozistore.model.Cliente;
import com.baozistore.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // POST /clientes - Criar cliente
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente salvo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /clientes - Listar todos
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    // GET /clientes/{id} - Consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // PUT /clientes/{id} - Atualizar (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id,
                                              @RequestBody Cliente dadosAtualizados) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(dadosAtualizados.getNome());
            cliente.setClienteDesde(dadosAtualizados.getClienteDesde());
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /clientes/{id} - Apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
