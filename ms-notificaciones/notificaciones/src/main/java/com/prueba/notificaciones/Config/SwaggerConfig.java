package com.prueba.notificaciones.Config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Bean
public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
            .title("API 2026 Gestion de Notificaciones")
            .version("1.0")
            .description("Documentacion de la API para el sistema de gestion de notificaciones"));
}

}
