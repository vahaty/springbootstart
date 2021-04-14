package com.viewnext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CalculadoraApplication{

    public static void main(final String[] args){
        SpringApplication.run(CalculadoraApplication.class, args);
    }

}
