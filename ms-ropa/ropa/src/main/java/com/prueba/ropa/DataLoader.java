package com.prueba.ropa;

import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Repository.RopaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private final RopaRepository ropaRepository;

    public DataLoader(RopaRepository ropaRepository) {
        this.ropaRepository = ropaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generamos 50 prendas
        for (int i = 0; i < 50; i++) {
            Ropa prenda = new Ropa();
            
            prenda.setNombre(faker.commerce().productName()); 
            
            prenda.setDetalle("Prenda de excelente calidad. Material: " + 
                               faker.commerce().material() + ", Color: ");
            
            // Precios aleatorios entre 4.990 y 49.990
            prenda.setPrecio(faker.number().numberBetween(4990, 49990)); 

            ropaRepository.save(prenda);
        }
    }
}