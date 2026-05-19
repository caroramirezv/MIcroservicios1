package com.prueba.ropa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class RopaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RopaApplication.class, args);
	}

}
