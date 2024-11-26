package org.example.microservicio2;

import org.example.microservicio2.Service.EnergyScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableDiscoveryClient
public class Microservicio2Application {

    private static final Logger logger = LoggerFactory.getLogger(Microservicio2Application.class);

    public static void main(String[] args) {
        // Inicia el contexto de la aplicación
        ConfigurableApplicationContext context = SpringApplication.run(Microservicio2Application.class, args);

        // Obtiene el bean del scheduler de energía
        EnergyScheduler energyScheduler = context.getBean(EnergyScheduler.class);

        // Inicia el monitoreo de medidores inteligentes
        logger.info("Iniciando monitoreo de medidores inteligentes...");
        energyScheduler.startMonitoring();

        logger.info("Microservicio de energía sostenible en ejecución.");
    }
}
