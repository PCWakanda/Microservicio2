package org.example.microservicio2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "energyFlowQueue")
    public void receiveEnergyFlowMessage(EnergyFlowMessage message) {
        System.out.println("Received energy flow message: " + message);
        // Procesar el mensaje de flujo de energía
    }
}