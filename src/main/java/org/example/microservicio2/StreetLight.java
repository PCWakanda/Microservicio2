package org.example.microservicio2;

import java.util.Random;

public class StreetLight {
    private final int id;
    private final String type;
    private final double baseConsumption;
    private final Random random;

    public StreetLight(int id, String type, double baseConsumption) {
        this.id = id;
        this.type = type;
        this.baseConsumption = baseConsumption;
        this.random = new Random();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getCurrentConsumption() {
        // Oscilación del consumo entre -10% y +10%
        return baseConsumption * (0.9 + (0.2 * random.nextDouble()));
    }
}