package org.example.microservicio2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.UUID;

@Entity
public class Energy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String type;
    private int amount;

    public Energy() {}

    public Energy(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
