package com.prueba.ropa.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Service.RopaService;

@RestController
@RequestMapping("/api/v1/ropa")
@Tag(name = "Ropas", description = "Gestionar la ropa ingresada")

public class RopaController {
    @Autowired
    private RopaService service;

    @PostMapping
    @Operation(summary ="Crea una ropa", description = "Crea una ropa desde 0")
    public ResponseEntity<Ropa> crear (@RequestBody Ropa ropa){
        return new ResponseEntity<>(service.save(ropa), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary ="Obtener toda la ropa", description = "Obtiene una lista de la ropa disponible")
    public ResponseEntity <List<Ropa>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary ="Obtener ropa por id", description = "Obtiene una lista de la ropa por su id")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarRopaCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Eliminar ropa por id", description = "Elimina una ropa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary="Actualizar ropa por id", description = "Actualiza una ropa existente")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "Ropa actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
                    
    })
        public ResponseEntity<Ropa> actualizar(
        @PathVariable Integer id,
        @RequestBody Ropa ropa) {
    return ResponseEntity.ok(service.update(id, ropa));
}

    @GetMapping("/buscar/{nombre}")
    @Operation(summary ="Obtener ropa por su nombre", description = "Obtiene ropa por su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa encontrada"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    public ResponseEntity<List<Ropa>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

}
