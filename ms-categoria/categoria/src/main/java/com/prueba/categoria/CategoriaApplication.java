package com.prueba.categoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class CategoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriaApplication.class, args);
	}

}
