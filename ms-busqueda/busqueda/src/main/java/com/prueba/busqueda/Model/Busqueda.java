package com.prueba.busqueda.Model;
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

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "busqueda")
public class Busqueda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String categoria;

    @ElementCollection @CollectionTable(name = "busqueda_resultados",
    joinColumns = @JoinColumn(name = "busqueda_id"))
    
    @Column(name = "ropa_id")
    private List<Integer> resultadosIds;
}
