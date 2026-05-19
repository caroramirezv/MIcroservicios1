package com.prueba.notificaciones.Controller;
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

import com.prueba.notificaciones.Model.Notificaciones;
import com.prueba.notificaciones.Service.NotificacionesService;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionesController {
 @Autowired
    private NotificacionesService service;

    @PostMapping
    public ResponseEntity<Notificaciones> crear (@RequestBody Notificaciones notificaciones){
        return new ResponseEntity<>(service.save(notificaciones), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Notificaciones>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarNotificacionesCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificaciones> actualizar(
        @PathVariable Integer id,
        @RequestBody Notificaciones notificaciones) {
    return ResponseEntity.ok(service.update(id, notificaciones));
    }
    
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Notificaciones>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }


}
