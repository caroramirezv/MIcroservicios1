package com.prueba.inventario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.inventario.Model.Inventario;
import com.prueba.inventario.Service.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")

public class InventarioController {
    @Autowired
    private InventarioService service;

    @PostMapping
    public ResponseEntity<Inventario> crear (@RequestBody Inventario inventario){
        return new ResponseEntity<>(service.save(inventario), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Inventario>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarInventarioCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar solo cantidad: PATCH /api/v1/inventario/{id}/cantidad/{valor}
    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<Inventario> actualizarCantidad(@PathVariable Integer id, @PathVariable Integer valor) {
        return ResponseEntity.ok(service.actualizarCantidad(id, valor));
    }

    // Verificar stock por ropaId: GET /api/v1/inventario/stock/{ropaId}
    @GetMapping("/stock/{ropaId}")
    public ResponseEntity<Map<String, Object>> verificarStock(@PathVariable Integer ropaId) {
        return ResponseEntity.ok(service.verificarStock(ropaId));
    }

    // Agregar PUT para CRUD completo: PUT /api/v1/inventario/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizar(
            @PathVariable Integer id, @RequestBody Inventario inventario) {
        inventario.setId(id);
        return ResponseEntity.ok(service.save(inventario));
    }


}
