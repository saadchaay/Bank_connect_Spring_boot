package com.bankconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class BankConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankConnectApplication.class, args);
    }
}
