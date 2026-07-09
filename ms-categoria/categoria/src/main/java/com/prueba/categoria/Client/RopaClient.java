package com.prueba.categoria.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prueba.categoria.Model.DTO.RopaDTO;
@FeignClient(name = "ropa")
public interface RopaClient {
    @GetMapping("/api/v1/ropa/{id}")
    RopaDTO obtenerRopaPorId(@PathVariable("id") Integer id);

}
