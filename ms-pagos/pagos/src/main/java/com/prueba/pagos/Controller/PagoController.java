package com.prueba.pagos.Controller;
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

import com.prueba.pagos.Model.Pago;
import com.prueba.pagos.Service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController 
@RequestMapping("/api/v1/pagos")
@Tag ( name = "pagos", description = "sistema de pago")
//http://localhost:8082/doc/swagger-ui/index.html
public class PagoController {
    @Autowired private PagoService service;

    @PostMapping
    @Operation(summary = "Crea un nuevo pago", description = "Crea un pago desde cero")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago creado")
    @ApiResponse(
        responseCode = "404", 
        description = "No se pudo crear")
    public ResponseEntity<Pago> procesar(@RequestBody Pago pago) {
        return new ResponseEntity<>(service.procesarPago(pago), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene una lista de los pagos")
    @ApiResponse(
        responseCode = "200", 
        description = "Pagos encontrados con exito")
    @ApiResponse(
        responseCode = "404", 
        description = "No encotramos pagos")
    public ResponseEntity<List<Pago>> listar() { 
        return ResponseEntity.ok(service.findAll());
     }

    @GetMapping("/{pedidoId}")
    @Operation(summary = "Obtener un pago por pedido id", description = "Obtiene una lista de los pagos por el id del pedido")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago encontrado")
    @ApiResponse(
        responseCode = "404", 
        description = "Pago no encontrado")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer pedidoId) {
        Map<String, Object> res = service.buscarPagoCompleto(pedidoId);
        return res.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @Operation (summary = "Actualizar un pago", description = "Elimina un pago por su ID")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago actualizado correctamente")
    @ApiResponse(
        responseCode = "404", 
        description = "Pago no encontrado")
    public ResponseEntity<Pago> actualizar(@PathVariable Integer id,
    @RequestBody Pago pago) {
    return ResponseEntity.ok(service.update(id, pago));
    }

    @DeleteMapping("/{id}")
    @Operation (summary = "Eliminar un pago", description = "Elimina un pago por su ID")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago eliminado correctamente")
    @ApiResponse(
        responseCode = "404", 
        description = "Pago no encontrado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{valor}")
    @Operation(summary = "Filtrar pagos por estado", description = "Obtiene una lista de pagos filtrados por estado")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago encontrado")
    @ApiResponse(
        responseCode = "404", 
        description = "Pago no encontrado")
    public ResponseEntity<List<Pago>> filtrarPorEstado(@PathVariable String valor) {
        return ResponseEntity.ok(service.filtrarPorEstado(valor));
    }

    @GetMapping("/metodoPago/{metodo}")
    @Operation(summary = "Filtrar por metodo de pago", description = "Obtiene una lista de pagos segun metodo de pago")
    @ApiResponse(
        responseCode = "200", 
        description = "Pago encontrado")
    @ApiResponse(
        responseCode = "404", 
        description = "Pago no encontrado")
    public ResponseEntity<List<Pago>> filtrarPorMetodoPago(@PathVariable String metodo) {
        return ResponseEntity.ok(service.filtrarPorEstado(metodo));
    }
}
