package com.prueba.ropa.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.ropa.Model.Ropa;

public interface RopaRepository extends JpaRepository<Ropa, Integer> {

    // Buscar por nombre 
    List<Ropa> findByNombre(String nombre);

    // Buscar ropa con precio menor o igual a X
    List<Ropa> findByPrecioLessThanEqual(int precioMax);
}
