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
@RestController @RequestMapping("/api/v1/pagos")
public class PagoController {
    @Autowired private PagoService service;

    @PostMapping
    public ResponseEntity<Pago> procesar(@RequestBody Pago pago) {
        return new ResponseEntity<>(service.procesarPago(pago), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listar() { 
        return ResponseEntity.ok(service.findAll());
     }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Map<String, Object>> obtenerCompleto(@PathVariable Integer pedidoId) {
        Map<String, Object> res = service.buscarPagoCompleto(pedidoId);
        return res.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Integer id,
    @RequestBody Pago pago) {
    return ResponseEntity.ok(service.update(id, pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{valor}")
    public ResponseEntity<List<Pago>> filtrarPorEstado(@PathVariable String valor) {
        return ResponseEntity.ok(service.filtrarPorEstado(valor));
    }

    @GetMapping("/metodoPago/{metodo}")
    public ResponseEntity<List<Pago>> filtrarPorMetodoPago(@PathVariable String metodo) {
        return ResponseEntity.ok(service.filtrarPorEstado(metodo));
    }
}
