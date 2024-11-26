package org.example.microservicio2.Service;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class RenewableEnergyService {

    public Flux<Double> getRenewableEnergyGeneration() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(tick -> Math.random() * 100); // Generación aleatoria entre 0 y 100 kWh
    }
}

