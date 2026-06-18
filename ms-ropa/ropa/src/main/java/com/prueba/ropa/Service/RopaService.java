package com.prueba.ropa.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Repository.RopaRepository;

@Service
public class RopaService {
    @Autowired
    private RopaRepository repository;

    public Ropa save(Ropa ropa) {
        return repository.save(ropa);
    }

    public List<Ropa> findAll() {
        return repository.findAll();
    }

    public Map<String, Object> buscarRopaCompleto(Integer id) {
    Ropa ropa = repository.findById(id).orElse(null);
    Map<String, Object> respuesta = new HashMap<>();
    if (ropa != null) {
        respuesta.put("id", ropa.getId());
        respuesta.put("nombre", ropa.getNombre());
        respuesta.put("detalle", ropa.getDetalle()); 
        respuesta.put("precio", ropa.getPrecio()); 
    }
    return respuesta;
}


    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Ropa update(Integer id, Ropa ropa) {
    if (!repository.existsById(id)) {
        throw new RuntimeException("Ropa no encontrada con id: " + id);
    }
    ropa.setId(id);
    return repository.save(ropa);
}

    // Buscar por nombre
    public List<Ropa> buscarPorNombre(String nombre) {
        return repository.findByNombre(nombre);
    }


}
