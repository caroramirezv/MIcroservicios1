package com.prueba.busqueda.Controller;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.busqueda.Model.Busqueda;
import com.prueba.busqueda.Service.BusquedaService;

@RestController
@RequestMapping("/api/v1/busqueda")
public class BusquedaController {
    @Autowired
    private BusquedaService service;

    @PostMapping
    public ResponseEntity<Busqueda> crear (@RequestBody Busqueda busqueda){
        return new ResponseEntity<>(service.save(busqueda), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Busqueda>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarBusquedaCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Busqueda>> filtrarPorCategoria(@RequestParam String valor) {
        return ResponseEntity.ok(service.filtrarPorCategoria(valor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Busqueda> actualizar(@PathVariable Integer id, @RequestBody Busqueda busqueda) {
        busqueda.setId(id);
        return ResponseEntity.ok(service.save(busqueda));
    }

}
