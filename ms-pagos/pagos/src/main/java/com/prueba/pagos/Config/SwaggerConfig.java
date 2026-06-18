package com.prueba.pagos.Config;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
     @Bean

  public OpenAPI customOpenAPI() {

    return new OpenAPI()

    .info(new Info()

   .title("API 2026 de gestion de pago")

   .version("1.0")

   .description("Pagos de Tienda Online "));

  }

}
