package com.baozistore.controller;

import com.baozistore.model.Pedido;
import com.baozistore.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // POST /pedidos - Criar pedido
    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /pedidos - Listar todos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    // GET /pedidos/{id} - Consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // PUT /pedidos/{id} - Atualizar (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id,
                                             @RequestBody Pedido dadosAtualizados) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setClienteId(dadosAtualizados.getClienteId());
            pedido.setProdutoId(dadosAtualizados.getProdutoId());
            pedido.setQuantidade(dadosAtualizados.getQuantidade());
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /pedidos/{id} - Apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
