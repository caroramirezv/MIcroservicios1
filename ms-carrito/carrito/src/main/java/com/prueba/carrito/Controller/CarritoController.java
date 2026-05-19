package com.prueba.carrito.Controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.carrito.Model.Carrito;
import com.prueba.carrito.Service.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {
 @Autowired
    private CarritoService service;

    @PostMapping
    public ResponseEntity<Carrito> crear (@RequestBody Carrito carrito){
        return new ResponseEntity<>(service.save(carrito), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Carrito>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarCarritoCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carrito>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Integer id, @RequestBody Carrito carrito) {
        carrito.setId(id);
        return ResponseEntity.ok(service.save(carrito));
    }

}
