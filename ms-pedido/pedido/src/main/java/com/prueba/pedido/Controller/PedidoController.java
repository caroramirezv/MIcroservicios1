package com.prueba.pedido.Controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.pedido.Model.Pedido;
import com.prueba.pedido.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {
    @Autowired private PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(service.save(pedido), HttpStatus.CREATED);
    }
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos")
    public ResponseEntity<List<Pedido>> listar() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener todos los pedidos segun su ID")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer id) {
        Map<String, Object> res = service.buscarPedidoCompleto(id);
        return res.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}/estado")//http://localhost:8083/api/v1/pedidos/1?Estado
    @Operation (summary = "Cambiar el estado de un pedido", description = "Cambia el estado de un pedido")
    @ApiResponse(
        responseCode = "200", 
        description = "Estado del pedido actualizado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)))
    @ApiResponse(
        responseCode = "404", 
        description = "Pedido no encontrado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Integer id, @RequestParam String estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Eliminar un pedido", description = "Elimina un pedido por su ID")
    @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente",
             content = @Content(mediaType = "application/json",
             schema = @Schema(implementation = Pedido.class)))
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener todos los pedidos segun el Id del Usuario")
    public ResponseEntity<List<Pedido>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @GetMapping("/estado/{valor}")
    @Operation(summary = "Obtener todos los pedidos segun el valor")
    public ResponseEntity<List<Pedido>> listarPorEstado(@PathVariable String valor) {
        return ResponseEntity.ok(service.findByEstado(valor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido segun su ID")
    @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente",
             content = @Content(mediaType = "application/json",
             schema = @Schema(implementation = Pedido.class)))
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    public ResponseEntity<Pedido> actualizar(
            @PathVariable Integer id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        return ResponseEntity.ok(service.save(pedido));
    }



}
