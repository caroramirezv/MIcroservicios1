package com.prueba.busqueda.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.busqueda.Client.RopaFeignClient;
import com.prueba.busqueda.Model.Busqueda;
import com.prueba.busqueda.Model.DTO.RopaDTO;
import com.prueba.busqueda.Repository.BusquedaRepository;

@Service
public class BusquedaService {
    @Autowired
    private BusquedaRepository repository;

    @Autowired
    private RopaFeignClient ropaClient;

    public Busqueda save(Busqueda busqueda) {
        return repository.save(busqueda);
    }

    public List<Busqueda> findAll() {
        return repository.findAll();
    }

    public Map<String, Object> buscarBusquedaCompleto(Integer id) {
    Busqueda busqueda = repository.findById(id).orElse(null);
    Map<String, Object> respuesta = new HashMap<>();
    if (busqueda != null) {
        List<RopaDTO> resultados = busqueda.getResultadosIds().stream()
            .map(ropaClient::obtenerRopaPorId)
            .collect(Collectors.toList());
        respuesta.put("id", busqueda.getId());
        respuesta.put("categoria", busqueda.getCategoria());
        respuesta.put("resultados", resultados);
    }
    return respuesta;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<Busqueda> filtrarPorCategoria(String categoria) {
    return repository.findByCategoria(categoria);
    }

}
