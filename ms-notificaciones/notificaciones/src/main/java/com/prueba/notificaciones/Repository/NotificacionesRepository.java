package com.prueba.notificaciones.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.notificaciones.Model.Notificaciones;
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Integer> {
    // Buscar notificaciones por nombre/tipo
    List<Notificaciones> findByNombreContainingIgnoreCase(String nombre);
}

