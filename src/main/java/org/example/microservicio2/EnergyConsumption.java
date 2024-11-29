package org.example.microservicio2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.UUID;

@Entity
public class EnergyConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int ledConsumption;
    private int solarConsumption;
    private int incandescentConsumption;

    public EnergyConsumption() {}

    public EnergyConsumption(int ledConsumption, int solarConsumption, int incandescentConsumption) {
        this.ledConsumption = ledConsumption;
        this.solarConsumption = solarConsumption;
        this.incandescentConsumption = incandescentConsumption;
    }

    public UUID getId() {
        return id;
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
