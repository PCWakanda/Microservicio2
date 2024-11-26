package org.example.microservicio2;

import java.io.Serializable;

public class EnergyFlowMessage implements Serializable {
    private String source;
    private double consumption;

    public EnergyFlowMessage() {}

    public EnergyFlowMessage(String source, double consumption) {
        this.source = source;
        this.consumption = consumption;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    @Override
    public String toString() {
        return "EnergyFlowMessage{" +
                "source='" + source + '\'' +
                ", consumption=" + consumption +
                '}';
    }
}