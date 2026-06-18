package com.prueba.pedido.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.pedido.Cliente.UsuarioFeignClient;
import com.prueba.pedido.Model.DTO.UsuarioDTO;
import com.prueba.pedido.Model.Pedido;
import com.prueba.pedido.Repository.PedidoRepository;

@Service
public class PedidoService {
    @Autowired private PedidoRepository repository;
    @Autowired private UsuarioFeignClient usuarioClient;

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }
    public List<Pedido> findAll() { return repository.findAll(); }

    public Map<String, Object> buscarPedidoCompleto(Integer id) {
        Pedido pedido = repository.findById(id).orElse(null);
        Map<String, Object> res = new HashMap<>();
        if (pedido != null) {
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(pedido.getUsuarioId());
            res.put("id", pedido.getId());
            res.put("cliente", usuario);
            res.put("fecha", pedido.getFecha());
            res.put("estado", pedido.getEstado());
            res.put("total", pedido.getTotal());
        }
        return res;
    }

    public Pedido cambiarEstado(Integer id, String estado) {
        Pedido pedido = repository.findById(id).orElseThrow();
        pedido.setEstado(estado);
        return repository.save(pedido);
    }

    public void delete(Integer id) {
        repository.findById(id).orElseThrow(() -> {return new RuntimeException("Pedido no encontrado");});
        repository.deleteById(id);
    }

    public List<Pedido> findByUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> findByEstado(String estado) {
        return repository.findByEstado(estado);
    }

}
