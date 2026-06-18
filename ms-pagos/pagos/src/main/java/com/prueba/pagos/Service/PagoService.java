package com.prueba.pagos.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.pagos.Client.PedidoFeignClient;
import com.prueba.pagos.Model.DTO.PedidoDTO;
import com.prueba.pagos.Model.Pago;
import com.prueba.pagos.Repository.PagoRepository;
@Service
public class PagoService {
    @Autowired private PagoRepository repository;
    @Autowired private PedidoFeignClient pedidoClient;

    public Pago procesarPago(Pago pago) {
        return repository.save(pago);
    }
    public List<Pago> findAll() { return repository.findAll(); }

    public Map<String, Object> buscarPagoCompleto(Integer pedidoId) {
        List<Pago> pagos = repository.findByPedidoId(pedidoId);
        Map<String, Object> res = new HashMap<>();
        if (!pagos.isEmpty()) {
            PedidoDTO pedido = pedidoClient.obtenerPedidoPorId(pedidoId);
            res.put("pago", pagos.get(0));
            res.put("pedido", pedido);
        }
        return res;
    }

    public void delete(Integer id) {
    repository.findById(id).orElseThrow(() -> {
        return new RuntimeException("Pedido no encontrado");
    });
    repository.deleteById(id);
    }

    public Pago update(Integer id, Pago pago) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ropa no encontrada con id: " + id);
        }
        pago.setId(id);
        return repository.save(pago);
    }

    public List<Pago> filtrarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public List<Pago> filtrarPorMetodPagos(String estado) {
        return repository.findByMetodoPago(estado);
    }

    
}

