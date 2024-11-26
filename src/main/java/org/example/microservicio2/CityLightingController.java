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
        // Add more street lights as needed
    }

    private void initializeEnergySources() {
        energySources.add(new EnergySource("Solar", 500));
        energySources.add(new EnergySource("Wind", 300));
        energySources.add(new EnergySource("Coal", 1000));
        // Add more energy sources as needed
    }

    public void manageCityLighting() {
        Flux.interval(Duration.ofSeconds(10))
            .doOnNext(tic -> {
                if (tic % 3 == 0) {
                    sendEnergySourceMessages();
                } else if (tic % 3 == 1) {
                    sendTotalConsumptionMessage();
                } else {
                    sendStreetLightMessages();
                }
            })
            .subscribeOn(Schedulers.parallel())
            .subscribe();
    }

    private void sendEnergySourceMessages() {
        for (EnergySource source : energySources) {
            sendEnergyFlowMessage(new EnergyFlowMessage(source.getName(), source.getCapacity()));
        }
    }

    private void sendTotalConsumptionMessage() {
        double totalConsumption = streetLights.stream()
            .mapToDouble(StreetLight::getCurrentConsumption)
            .sum();
        sendEnergyFlowMessage(new EnergyFlowMessage("Total", totalConsumption));
    }

    private void sendStreetLightMessages() {
        for (StreetLight light : streetLights) {
            sendEnergyFlowMessage(new EnergyFlowMessage("StreetLight " + light.getId(), light.getCurrentConsumption()));
        }
    }

    private void sendEnergyFlowMessage(EnergyFlowMessage message) {
        rabbitTemplate.convertAndSend("energyFlowQueue", message);
    }
}