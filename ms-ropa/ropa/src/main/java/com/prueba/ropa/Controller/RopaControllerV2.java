package com.prueba.ropa.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

@RestController
@RequestMapping("/api/v2/ropa")
public class RopaControllerV2 {

    @Autowired
    private RopaService service;

    @Autowired
    private RopaModelAssembler assembler;

    @Operation(summary = "Obtener toda la ropa", description = "Obtiene una lista de la ropa disponible")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se encontraron ropas"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Ropa>> getAllRopas() {
        List<EntityModel<Ropa>> ropas = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ropas,
                linkTo(methodOn(RopaControllerV2.class).getAllRopas()).withSelfRel());
    }

    @Operation(summary = "Obtener ropa por id", description = "Obtiene una ropa por su id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ropa encontrada con exito"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })   
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Ropa> getRopaById(@PathVariable Integer id) {
        Ropa ropa = service.findById(id); 
        
        return assembler.toModel(ropa);
    }

    @Operation(summary = "Obtener ropa por su nombre", description = "Obtiene ropa por su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ropa encontrada"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    @GetMapping(value = "/buscar/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Ropa>> getRopasByNombre(@PathVariable String nombre) {
        List<EntityModel<Ropa>> ropas = service.buscarPorNombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ropas,
                linkTo(methodOn(RopaControllerV2.class).getRopasByNombre(nombre)).withSelfRel());
    }

    @Operation(summary = "Crea una ropa", description = "Crea una ropa desde 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ropa creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Ropa no se pudo crear, intentelo nuevamente")
    })   
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ropa>> createRopa(@RequestBody Ropa ropa) {
        Ropa newRopa = service.save(ropa);
        return ResponseEntity
                .created(linkTo(methodOn(RopaControllerV2.class).getRopaById(newRopa.getId())).toUri())
                .body(assembler.toModel(newRopa));
    }

    @Operation(summary = "Actualizar ropa por id", description = "Actualiza una ropa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ropa actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ropa>> updateRopa(@PathVariable Integer id, @RequestBody Ropa ropa) {
        ropa.setId(id);
        Ropa updatedRopa = service.update(id, ropa);
        return ResponseEntity.ok(assembler.toModel(updatedRopa));
    }

    @Operation(summary = "Eliminar ropa por id", description = "Elimina una ropa existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ropa eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Ropa no encontrada")
    })      
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteRopa(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}