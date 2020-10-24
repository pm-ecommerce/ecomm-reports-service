package com.pm.ecommerce.reports_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.pm.ecommerce.entities"})
public class ReportsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportsServiceApplication.class, args);
    }

}
