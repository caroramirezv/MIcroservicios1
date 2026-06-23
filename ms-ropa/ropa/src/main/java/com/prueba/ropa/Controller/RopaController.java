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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ropa")
@Tag(name = "Ropas", description = "Gestionar la ropa ingresada")


public class RopaController {
    @Autowired
    private RopaService service;

    @PostMapping
    @Operation(summary ="Crea una ropa", description = "Crea una ropa desde 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa creada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no se pudo crear, intentelo nuevamente")
    })   
    public ResponseEntity<Ropa> crear (@RequestBody Ropa ropa){
        return new ResponseEntity<>(service.save(ropa), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se encontraron ropas"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    @Operation(summary ="Obtener toda la ropa", description = "Obtiene una lista de la ropa disponible")
    public ResponseEntity <List<Ropa>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary ="Obtener ropa por id", description = "Obtiene una lista de la ropa por su id")@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa encontrada con exito"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })   
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
