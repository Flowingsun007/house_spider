package com.flowingbit.data.collect.house_spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class HouseSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseSpiderApplication.class, args);
    }

}
