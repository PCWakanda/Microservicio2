package org.example.microservicio2;

public class EnergyConsumption {
    private int ledConsumption;
    private int solarConsumption;
    private int incandescentConsumption;

    public EnergyConsumption(int ledConsumption, int solarConsumption, int incandescentConsumption) {
        this.ledConsumption = ledConsumption;
        this.solarConsumption = solarConsumption;
        this.incandescentConsumption = incandescentConsumption;
    }

    public int getLedConsumption() {
        return ledConsumption;
    }

    public int getSolarConsumption() {
        return solarConsumption;
    }

    public int getIncandescentConsumption() {
        return incandescentConsumption;
    }
}