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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name= "Notificaciones", description = "Operaciones relacionadas con las Notificaciones")
//http://localhost:8082/doc/swagger-ui/index.html
public class NotificacionesController {
 @Autowired
    private NotificacionesService service;

    @PostMapping
    @Operation(summary = "Crea una nueva Notificacion", description = "Crea una Notificacion desde 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion creada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificacion no creada")
        })
    public ResponseEntity<Notificaciones> crear (@RequestBody Notificaciones notificaciones){
        return new ResponseEntity<>(service.save(notificaciones), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "obtener todas las notificaciones", description = "Obtiene una lista de todas las notificaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion encotrada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificaciones no encotradas")
        })
    public ResponseEntity <List<Notificaciones>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "obtener notificaciones por ID", description = "Obtiene notificaciones por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion encontrada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
        })
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarNotificacionesCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una notificacion por ID", description = "Elimina una notificacion segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion eliminada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
        })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una notificacion por ID", description = "Actualiza una notificacion segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion actualizada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
        })
    public ResponseEntity<Notificaciones> actualizar(
        @PathVariable Integer id,
        @RequestBody Notificaciones notificaciones) {
    return ResponseEntity.ok(service.update(id, notificaciones));
    }
    
    @GetMapping("/buscar/{nombre}")
    @Operation(summary = "obtener notificaiones por su nombre", description = "Obtiene una notificacion segun su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion encontrada con exito"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
        })
    public ResponseEntity<List<Notificaciones>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }


}
