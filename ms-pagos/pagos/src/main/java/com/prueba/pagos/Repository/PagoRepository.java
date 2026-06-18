package com.prueba.pagos.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.pagos.Model.Pago;
@Repository

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByPedidoId(Integer pedidoId);

    List<Pago> findByEstado(String estado); 

    List<Pago> findByMetodoPago(String metodoPago);  
}

