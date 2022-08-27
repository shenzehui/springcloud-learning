package org.javaboy.comsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ComsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComsumerApplication.class, args);
    }

    @Bean
    RestTemplate restTemplateOne(){
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
