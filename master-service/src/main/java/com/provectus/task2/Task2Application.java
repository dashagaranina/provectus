package com.provectus.task2;

import feign.Request;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class Task2Application {

    public static void main(String[] args) {
        SpringApplication.run(Task2Application.class, args);
    }


    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

}
