package com.prueba.busqueda.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prueba.busqueda.Model.DTO.RopaDTO;

@FeignClient(name = "ms-ropa", url = "localhost:8082") 
public interface RopaFeignClient {
    @GetMapping("/api/v1/ropa/{id}") 
    RopaDTO obtenerRopaPorId(@PathVariable("id") Integer id);
}


