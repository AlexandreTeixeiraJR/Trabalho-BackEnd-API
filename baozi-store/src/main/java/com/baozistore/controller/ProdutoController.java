package com.baozistore.controller;

import com.baozistore.model.Produto;
import com.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // POST /produtos - Criar produto
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto salvo = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /produtos - Listar todos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return ResponseEntity.ok(produtos);
    }

    // GET /produtos/{id} - Consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // PUT /produtos/{id} - Atualizar (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id,
                                              @RequestBody Produto dadosAtualizados) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(dadosAtualizados.getNome());
            produto.setPreco(dadosAtualizados.getPreco());
            produto.setEstoque(dadosAtualizados.getEstoque());
            return ResponseEntity.ok(produtoRepository.save(produto));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /produtos/{id} - Apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
