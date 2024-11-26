package org.example.microservicio2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Component
public class MasterScheduler {

    private final CityLightingController cityLightingController;
    private Disposable disposable;

    @Autowired
    public MasterScheduler(CityLightingController cityLightingController) {
        this.cityLightingController = cityLightingController;
    }

    public void startSequentialFlows() {
        disposable = Flux.interval(Duration.ofSeconds(4))
                .doOnNext(tic -> {
                    cityLightingController.manageCityLighting();
                })
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }
}