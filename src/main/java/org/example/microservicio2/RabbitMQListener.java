package org.example.microservicio2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "logQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}