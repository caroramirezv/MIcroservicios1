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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/busqueda")
@Tag(name= "busqueda", description = "Operaciones relacionadas con la busqueda")
public class BusquedaController {
    @Autowired
    private BusquedaService service;

    @PostMapping
    @Operation (summary = "Crear busqueda", description = "Crear busqueda desde cero")
    @ApiResponse(responseCode = "200", description = "Busqueda creada")
    @ApiResponse(responseCode = "404", description = "No se pudo crear esa busqueda")
    public ResponseEntity<Busqueda> crear (@RequestBody Busqueda busqueda){
        return new ResponseEntity<>(service.save(busqueda), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation (summary = "Lista todas las busquedas", description = "Obtiene todas las busquedas")
    @ApiResponse(responseCode = "200", description = "Busquedas encontradas")
    @ApiResponse(responseCode = "404", description = "No se encontro busquedas")
    public ResponseEntity <List<Busqueda>> listar(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation (summary = "Obtener busqueda", description = "Obtener busqueda por id ")
    @ApiResponse(responseCode = "200", description = "Busquedas encontradas")
    @ApiResponse(responseCode = "404", description = "No se encontro busquedas con esa id")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id){
        Map<String, Object> respuesta = service.buscarBusquedaCompleto(id);
        return respuesta.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(respuesta); 
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Eliminar busqueda", description = "Eliminar busqueda por id")
    @ApiResponse(responseCode = "200", description = "Busqueda eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "No se pudoeliminar esa busqueda")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria")
    @Operation (summary = "Buscar por categoria", description = "Filtrado por categoria ")
    @ApiResponse(responseCode = "200", description = "Categorias encontradas")
    @ApiResponse(responseCode = "404", description = "No se encontro esa categoria en busqueda")
    public ResponseEntity<List<Busqueda>> filtrarPorCategoria(@RequestParam String valor) {
        return ResponseEntity.ok(service.filtrarPorCategoria(valor));
    }

    @PutMapping("/{id}")
    @Operation (summary = "Actualizar busqueda", description = "Actualizar busqueda por id")
    @ApiResponse(responseCode = "200", description = "Busqueda actualizada")
    @ApiResponse(responseCode = "404", description = "No se pudo actualizar la busqueda")
    public ResponseEntity<Busqueda> actualizar(@PathVariable Integer id, @RequestBody Busqueda busqueda) {
        busqueda.setId(id);
        return ResponseEntity.ok(service.save(busqueda));
    }

}
