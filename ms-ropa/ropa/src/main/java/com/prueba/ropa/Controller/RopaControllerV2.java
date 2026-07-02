package com.prueba.ropa.Controller;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import com.prueba.ropa.Assemblers.RopaModelAssembler;
import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Service.RopaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ropa")
@Tag(name = "Ropas", description = "Gestionar la ropa ingresada")

public class RopaControllerV2 {
  @Autowired
    private RopaService service;

    @Autowired
    private RopaModelAssembler assembler;

    @PostMapping
    @Operation(summary ="Crea una ropa", description = "Crea una ropa desde 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa creada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no se pudo crear, intentelo nuevamente")
    })   
    public ResponseEntity<EntityModel<Ropa>> crear (@RequestBody Ropa ropa){
        EntityModel<Ropa> modelo = assembler.toModel(service.save(ropa));
        return new ResponseEntity<>(modelo, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Se encontraron ropas"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    @Operation(summary ="Obtener toda la ropa", description = "Obtiene una lista de la ropa disponible")
    public ResponseEntity<CollectionModel<EntityModel<Ropa>>> listar(){
        List<EntityModel<Ropa>> ropas = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Ropa>> coleccion = CollectionModel.of(ropas,
                linkTo(methodOn(RopaControllerV2.class).listar()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/{id}")
    @Operation(summary ="Obtener ropa por id", description = "Obtiene una lista de la ropa por su id")@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa encontrada con exito"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })   
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarRopaCompleto(id);
        if (respuesta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link self = linkTo(methodOn(RopaControllerV2.class).obtenerCompleto(id)).withSelfRel();
        Link ropas = linkTo(methodOn(RopaControllerV2.class).listar()).withRel("ropa");
        respuesta.put("_links", List.of(self, ropas));

        return ResponseEntity.ok(respuesta);
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
    public ResponseEntity<EntityModel<Ropa>> actualizar(
        @PathVariable Integer id,
        @RequestBody Ropa ropa) {
    EntityModel<Ropa> modelo = assembler.toModel(service.update(id, ropa));
    return ResponseEntity.ok(modelo);
}

    @GetMapping("/buscar/{nombre}")
    @Operation(summary ="Obtener ropa por su nombre", description = "Obtiene ropa por su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa encontrada"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    public ResponseEntity<CollectionModel<EntityModel<Ropa>>> buscarPorNombre(@PathVariable String nombre) {
        List<EntityModel<Ropa>> ropas = service.buscarPorNombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Ropa>> coleccion = CollectionModel.of(ropas,
                linkTo(methodOn(RopaControllerV2.class).buscarPorNombre(nombre)).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }
}
