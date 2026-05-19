package com.prueba.envio.Controller;
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

import com.prueba.envio.Model.Envio;
import com.prueba.envio.Service.EnvioService;

@RestController
@RequestMapping("/api/v1/envio")

public class EnvioController {
    @Autowired
    private EnvioService service;

    @PostMapping
    public ResponseEntity<Envio> crear (@RequestBody Envio envio){
        return new ResponseEntity<>(service.save(envio), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Envio>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarEnvioCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.filtrarPorEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Integer id, @RequestBody Envio envio) {
        envio.setId(id);
        return ResponseEntity.ok(service.save(envio));
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<List<Envio>> buscarPorPedido(@PathVariable Integer id) {
    return ResponseEntity.ok(service.filtrarPorPedido(id));
}

}
