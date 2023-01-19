package com.example.securitytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SecurityTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityTestApplication.class, args);
    }

}
