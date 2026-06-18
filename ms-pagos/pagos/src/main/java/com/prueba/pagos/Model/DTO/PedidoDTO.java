package com.prueba.pagos.Model.DTO;
import lombok.Data;


@Data
public class PedidoDTO {
    private Integer id;
    private Double total;
    private String estado;
}
