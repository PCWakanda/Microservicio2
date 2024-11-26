package org.example.microservicio2.Service;


import org.example.microservicio2.Model.Light;

import org.example.microservicio2.Model.SmartMeter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class EnergyScheduler {

    private final Sinks.Many<SmartMeter> smartMeterSink = Sinks.many().multicast().onBackpressureBuffer();
    private final RabbitTemplate rabbitTemplate;
    private final List<SmartMeter> smartMeters = new ArrayList<>();
    private final Random random = new Random();

    @Autowired
    public EnergyScheduler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        initSmartMeters();
        startMonitoring();
    }

    private void initSmartMeters() {
        for (int i = 0; i < 5; i++) {
            smartMeters.add(new SmartMeter("Location " + (i + 1)));
        }
    }

    public Flux<SmartMeter> getSmartMeterFlux() {
        return smartMeterSink.asFlux();
    }

    public void startMonitoring() {
        Flux.interval(Duration.ofSeconds(4))
                .subscribe(tick -> {
                    for (SmartMeter meter : smartMeters) {
                        double randomConsumption = random.nextDouble() * 5;
                        meter.incrementConsumption(randomConsumption);

                        if (meter.shouldAlert()) {
                            rabbitTemplate.convertAndSend("logQueue", "ALERTA: Consumo alto detectado en " + meter.getLocation());
                        }

                        smartMeterSink.tryEmitNext(meter);
                    }
                });
    }
}
