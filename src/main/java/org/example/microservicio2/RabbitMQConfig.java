package org.example.microservicio2;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue energyFlowQueue() {
        return new Queue("energyFlowQueue", false);
    }
}