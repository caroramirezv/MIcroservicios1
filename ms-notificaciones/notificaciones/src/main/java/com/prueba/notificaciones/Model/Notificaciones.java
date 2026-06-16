package com.prueba.notificaciones.Model;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "notificaciones")
public class Notificaciones {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

        @Column(nullable = false)
    private String detalle;

    @ElementCollection
    @CollectionTable(name = "notificaciones_usuario_ids", joinColumns = @JoinColumn(name = "notificaciones_id"))
    @Column(name = "usuario_id")
    private List<Integer> usuarioIds;
}
