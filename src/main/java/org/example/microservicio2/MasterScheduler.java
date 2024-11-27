package org.example.microservicio2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Component
public class MasterScheduler {

    private final EnergyController energyController;
    private Disposable disposable;
    private int ticsTotales = 0;

    @Autowired
    public MasterScheduler(EnergyController energyController) {
        this.energyController = energyController;
    }

    public void startSequentialFlows() {
        disposable = Flux.interval(Duration.ofSeconds(4), Schedulers.newSingle("master-scheduler"))
                .doOnNext(tic -> {
                    ticsTotales++;
                    energyController.incrementTick();
                })
                .subscribe();
    }
}