package com.prueba.busqueda.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.busqueda.Model.Busqueda;
public interface BusquedaRepository extends JpaRepository<Busqueda, Integer> {
    List<Busqueda> findByCategoria(String categoria);
}

