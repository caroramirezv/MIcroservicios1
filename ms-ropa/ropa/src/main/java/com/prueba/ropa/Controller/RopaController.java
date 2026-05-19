package com.prueba.ropa.Controller;

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

import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Service.RopaService;

@RestController
@RequestMapping("/api/v1/ropa")

public class RopaController {
    @Autowired
    private RopaService service;

    @PostMapping
    public ResponseEntity<Ropa> crear (@RequestBody Ropa ropa){
        return new ResponseEntity<>(service.save(ropa), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Ropa>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarRopaCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
        public ResponseEntity<Ropa> actualizar(
        @PathVariable Integer id,
        @RequestBody Ropa ropa) {
    return ResponseEntity.ok(service.update(id, ropa));
}

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Ropa>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

}
