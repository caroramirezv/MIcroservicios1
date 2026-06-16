package com.prueba.notificaciones.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prueba.notificaciones.Model.DTO.UsuarioDTO;
@FeignClient(name = "usuario", url = "localhost:8081")
public interface UsuarioFeignNotificaciones {
    @GetMapping("/api/v1/usuario/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Integer id);
}

