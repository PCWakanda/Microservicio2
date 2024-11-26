package org.example.microservicio2.Model;

import java.util.UUID;

public class SmartMeter {
    private String id;
    private String location;
    private double currentConsumption; // Consumo actual en kWh
    private double totalConsumption;   // Consumo total acumulado
    private int tick;

    public SmartMeter(String location) {
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.currentConsumption = 0.0;
        this.totalConsumption = 0.0;
        this.tick = 0;
    }
    public String getLocation() {
        return location;
    }


    public void incrementConsumption(double value) {
        this.currentConsumption += value;
        this.totalConsumption += value;
    }

    public void resetCurrentConsumption() {
        this.currentConsumption = 0.0;
    }

    public boolean shouldAlert() {
        return currentConsumption > 10.0; // Ejemplo: alerta si supera 10 kWh
    }

    // Getters y Setters
}
