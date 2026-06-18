package com.prueba.pedido.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prueba.pedido.Model.DTO.UsuarioDTO;

@FeignClient(name = "usuario", url = "localhost:8081")
public interface UsuarioFeignClient {
    @GetMapping("/api/v1/usuario/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Integer id);
}

