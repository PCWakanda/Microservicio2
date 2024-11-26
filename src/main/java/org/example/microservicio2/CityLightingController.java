package org.example.microservicio2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityLightingController {
    private final List<StreetLight> streetLights;
    private final List<EnergySource> energySources;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CityLightingController(RabbitTemplate rabbitTemplate) {
        this.streetLights = new ArrayList<>();
        this.energySources = new ArrayList<>();
        this.rabbitTemplate = rabbitTemplate;
        initializeStreetLights();
        initializeEnergySources();
    }

    private void initializeStreetLights() {
        streetLights.add(new StreetLight(1, "LED", 50));
        streetLights.add(new StreetLight(2, "Halogen", 150));
        streetLights.add(new StreetLight(3, "Fluorescent", 100));
        // Agregar más farolas según sea necesario
    }

    private void initializeEnergySources() {
        energySources.add(new EnergySource("Solar", 500));
        energySources.add(new EnergySource("Wind", 300));
        energySources.add(new EnergySource("Coal", 1000));
        // Agregar más fuentes de energía según sea necesario
    }

    public void manageCityLighting() {
        Flux.interval(Duration.ofSeconds(10))
            .doOnNext(tic -> {
                double totalConsumption = streetLights.stream()
                    .mapToDouble(StreetLight::getCurrentConsumption)
                    .sum();
                System.out.println("Consumo total de energía: " + totalConsumption + " kW");
                distributeEnergy(totalConsumption);
            })
            .subscribeOn(Schedulers.parallel())
            .subscribe();
    }

    private void distributeEnergy(double totalConsumption) {
        double remainingConsumption = totalConsumption;
        for (EnergySource source : energySources) {
            if (remainingConsumption <= 0) break;
            double usedCapacity = Math.min(source.getCapacity(), remainingConsumption);
            remainingConsumption -= usedCapacity;
            System.out.println("Usando " + usedCapacity + " kW de " + source.getName());
            sendEnergyFlowMessage(new EnergyFlowMessage(source.getName(), usedCapacity));
        }
        if (remainingConsumption > 0) {
            System.out.println("Advertencia: No hay suficiente capacidad de energía para cubrir el consumo total.");
        }
    }

    private void sendEnergyFlowMessage(EnergyFlowMessage message) {
        rabbitTemplate.convertAndSend("energyFlowQueue", message);
    }
}