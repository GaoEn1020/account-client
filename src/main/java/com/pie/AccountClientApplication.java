package com.pie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pie.account","pie.microservice.rpc"})
public class AccountClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountClientApplication.class, args);
    }
}
