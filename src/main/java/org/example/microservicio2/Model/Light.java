package org.example.microservicio2.Model;


import java.util.UUID;

public class Light {
    private String id;
    private String location;
    private boolean isOn;

    public Light(String location) {
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.isOn = false;
    }

    public void toggle() {
        this.isOn = !this.isOn;
    }

    // Getters y Setters
}

