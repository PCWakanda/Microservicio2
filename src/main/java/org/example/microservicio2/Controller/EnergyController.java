package org.example.microservicio2.Controller;

import org.example.microservicio2.Service.EnergyScheduler;
import org.example.microservicio2.Model.SmartMeter; // Asegúrate de importar la clase correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EnergyController {

    private final EnergyScheduler energyScheduler;

    @Autowired
    public EnergyController(EnergyScheduler energyScheduler) {
        this.energyScheduler = energyScheduler;
    }

    @GetMapping("/smart-meters")
    public Flux<SmartMeter> getSmartMeterData() {
        return energyScheduler.getSmartMeterFlux();
    }
}