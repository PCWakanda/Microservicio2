package org.example.microservicio2;

public class EnergySource {
    private final String name;
    private final double capacity; // Capacidad en kW

    public EnergySource(String name, double capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public double getCapacity() {
        return capacity;
    }
}