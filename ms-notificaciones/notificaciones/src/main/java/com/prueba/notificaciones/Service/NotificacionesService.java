package com.prueba.notificaciones.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.notificaciones.Client.UsuarioFeignNotificaciones;
import com.prueba.notificaciones.Model.DTO.UsuarioDTO;
import com.prueba.notificaciones.Model.Notificaciones;
import com.prueba.notificaciones.Repository.NotificacionesRepository;

@Service
public class NotificacionesService {
 @Autowired
    private NotificacionesRepository repository;

    @Autowired
    private UsuarioFeignNotificaciones usuarioClient;

    public Notificaciones save(Notificaciones notificaciones) {
        return repository.save(notificaciones);
    }

    public List<Notificaciones> findAll() {
        return repository.findAll();
    }

    public Map<String, Object> buscarNotificacionesCompleto(Integer id) {
        Notificaciones notificaciones = repository.findById(id).orElse(null);
        Map<String, Object> respuesta = new HashMap<>();
        if (notificaciones != null) {
            List<UsuarioDTO> usuario = notificaciones.getUsuarioIds().stream()
                    .map(usuarioClient::obtenerUsuarioPorId)
                    .collect(Collectors.toList());
            respuesta.put("id", notificaciones.getId());
            respuesta.put("nombre", notificaciones.getNombre());
            respuesta.put("usuario", usuario);
        }
        return respuesta;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Notificaciones update(Integer id, Notificaciones notificaciones) {
    repository.findById(id).orElseThrow(() ->
        new RuntimeException("No encontrado id: " + id));
    notificaciones.setId(id);
    return repository.save(notificaciones);
    }

    public List<Notificaciones> buscarPorNombre(String nombre) {
    return repository.findByNombreContainingIgnoreCase(nombre);
    }


}
