package org.example.microservicio2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class Microservicio2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Microservicio2Application.class, args);
        MasterScheduler masterScheduler = context.getBean(MasterScheduler.class);
        masterScheduler.startSequentialFlows();
    }
}