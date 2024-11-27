package org.example.microservicio2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class EnergyController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);

    private final Sinks.Many<Energy> renewableSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Energy> nonRenewableSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<EnergyConsumption> consumptionSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Random random = new Random();
    private final RabbitTemplate rabbitTemplate;
    private final List<Energy> renewableEnergies = new ArrayList<>();
    private final List<Energy> nonRenewableEnergies = new ArrayList<>();
    private final List<EnergyConsumption> consumptions = new ArrayList<>();
    private int tickCount = 0;
    private final Scheduler scheduler = Schedulers.newSingle("energy-scheduler");

    @Autowired
    public EnergyController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/renewableEnergy")
    public Flux<Energy> getRenewableEnergyFlow() {
        return renewableSink.asFlux();
    }

    @GetMapping("/nonRenewableEnergy")
    public Flux<Energy> getNonRenewableEnergyFlow() {
        return nonRenewableSink.asFlux();
    }

    @GetMapping("/energyConsumption")
    public Flux<EnergyConsumption> getEnergyConsumptionFlow() {
        return consumptionSink.asFlux();
    }

    public void startEnergyFlow() {
        Flux.interval(Duration.ofSeconds(4), scheduler)
                .subscribe(tick -> {
                    tickCount++;
                    logger.info("----tic {}----", tickCount);

                    // Generate renewable energy
                    int renewableEnergyAmount = random.nextInt(50) + 50; // kW
                    Energy renewableEnergy = new Energy("renovable", renewableEnergyAmount);
                    renewableEnergies.add(renewableEnergy);
                    renewableSink.tryEmitNext(renewableEnergy);
                    logger.info("Energía renovable generada: {} kW", renewableEnergyAmount);

                    // Generate non-renewable energy
                    int nonRenewableEnergyAmount = random.nextInt(100) + 100; // kW
                    Energy nonRenewableEnergy = new Energy("no renovable", nonRenewableEnergyAmount);
                    nonRenewableEnergies.add(nonRenewableEnergy);
                    nonRenewableSink.tryEmitNext(nonRenewableEnergy);
                    logger.info("Energía no renovable generada: {} kW", nonRenewableEnergyAmount);

                    // Calculate total energy
                    int totalEnergy = renewableEnergyAmount + nonRenewableEnergyAmount;
                    logger.info("Energía total disponible: {} kW", totalEnergy);

                    // Generate energy consumption
                    int ledConsumption = random.nextInt(5) + 5; // kW
                    int solarConsumption = random.nextInt(5) + 5; // kW
                    int incandescentConsumption = random.nextInt(10) + 10; // kW
                    EnergyConsumption consumption = new EnergyConsumption(ledConsumption, solarConsumption, incandescentConsumption);
                    consumptions.add(consumption);
                    consumptionSink.tryEmitNext(consumption);
                    logger.info("Consumo de energía - LED: {} kW, Solar: {} kW, Incandescente: {} kW", ledConsumption, solarConsumption, incandescentConsumption);

                    // Calculate total consumption
                    int totalConsumption = ledConsumption + solarConsumption + incandescentConsumption;
                    logger.info("Energía total consumida: {} kW", totalConsumption);
                });
    }
}