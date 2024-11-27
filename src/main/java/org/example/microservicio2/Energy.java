package org.example.microservicio2;

public class Energy {
    private String type;
    private int amount;

    public Energy(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}